package org.pedrojaraujo.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<ValidPriority, Integer> {

    @Override
    public void initialize(ValidPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && (value == 1 || value == 2 || value == 3);
    }
}
