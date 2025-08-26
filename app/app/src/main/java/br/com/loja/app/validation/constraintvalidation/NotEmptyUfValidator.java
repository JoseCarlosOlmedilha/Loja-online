package br.com.loja.app.validation.constraintvalidation;


import br.com.loja.app.validation.NotEmptyUF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyUfValidator implements ConstraintValidator<NotEmptyUF, Enum<?>> {

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        // Implement your validation logic here
        return value != null && value.name() != null && !value.name().trim().isEmpty();
    }

    @Override
    public void initialize(NotEmptyUF constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
