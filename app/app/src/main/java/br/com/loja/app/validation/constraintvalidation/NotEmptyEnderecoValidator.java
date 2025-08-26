package br.com.loja.app.validation.constraintvalidation;

import br.com.loja.app.dtos.EnderecoDTO;
import br.com.loja.app.validation.NotEmptyEndereco;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyEnderecoValidator implements ConstraintValidator<NotEmptyEndereco, EnderecoDTO> {

    @Override
    public boolean isValid(EnderecoDTO endereco, ConstraintValidatorContext context) {

        if (endereco == null) {
        return false;
    }

    boolean valid = true;

    if (endereco.getBairro() == null || endereco.getBairro().isBlank()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("O campo 'bairro' não pode ser nulo ou vazio")
               .addConstraintViolation();
        valid = false;
    }

    if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("O campo 'cidade' não pode ser nulo ou vazio")
               .addConstraintViolation();
        valid = false;
    }

    if (endereco.getCep() == null || endereco.getCep().isBlank()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("O campo 'CEP' não pode ser nulo ou vazio")
               .addConstraintViolation();
        valid = false;
    }

    if (endereco.getNumero() == null || endereco.getNumero().isBlank()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("O campo 'número' não pode ser nulo ou vazio")
               .addConstraintViolation();
        valid = false;
    }

    if (endereco.getRua() == null || endereco.getRua().isBlank()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("O campo 'rua' não pode ser nulo ou vazio")
               .addConstraintViolation();
        valid = false;
    }

    return valid;
    }




}
