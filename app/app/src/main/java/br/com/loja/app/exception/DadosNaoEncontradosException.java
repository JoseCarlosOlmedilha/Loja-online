package br.com.loja.app.exception;

public class DadosNaoEncontradosException extends RuntimeException {

    public DadosNaoEncontradosException(String message) {
        super(message);
    }
    public DadosNaoEncontradosException(String message, Throwable cause) {
        super(message, cause);
    }
}
