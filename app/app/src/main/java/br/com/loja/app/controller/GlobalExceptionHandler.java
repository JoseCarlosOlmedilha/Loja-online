package br.com.loja.app.controller;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.loja.app.exception.CampoNuloException;
import br.com.loja.app.exception.DadosInvalidosException;
import br.com.loja.app.exception.DadosNaoEncontradosException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<Map<String, String>> handleDadosInvalidos(DadosInvalidosException ex) {
        return criarErro(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CampoNuloException.class)
    public ResponseEntity<Map<String, String>> handleCampoNulo(CampoNuloException ex) {
        return criarErro(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DadosNaoEncontradosException.class)
    public ResponseEntity<Map<String, String>> handleDadosNaoEncontrados(DadosNaoEncontradosException ex) {
        return criarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handler genérico para erros inesperados
    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return criarErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado");
    }
    */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Coleta todos os erros de campo
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

      // Método auxiliar para montar o corpo do erro
    private ResponseEntity<Map<String, String>> criarErro(HttpStatus status, String mensagem) {
        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", mensagem);
        erro.put("status", String.valueOf(status.value()));
        erro.put("erro", status.getReasonPhrase());
        return ResponseEntity.status(status).body(erro);
    }

}
