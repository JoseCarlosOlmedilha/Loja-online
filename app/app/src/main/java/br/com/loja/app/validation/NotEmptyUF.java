package br.com.loja.app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.loja.app.validation.constraintvalidation.NotEmptyUfValidator;
import jakarta.validation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyUfValidator.class)
public @interface NotEmptyUF {

    String message() default "UF não pode ser vazio ";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
