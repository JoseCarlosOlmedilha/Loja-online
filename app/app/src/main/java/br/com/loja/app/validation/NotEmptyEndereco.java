package br.com.loja.app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.loja.app.validation.constraintvalidation.NotEmptyEnderecoValidator;
import jakarta.validation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyEnderecoValidator.class)
public @interface NotEmptyEndereco {

    String message() default "Endereço não pode ser vazio";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
