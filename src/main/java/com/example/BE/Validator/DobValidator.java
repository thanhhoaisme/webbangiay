package com.example.BE.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int minAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return true;
        }
        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= minAge;
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        minAge = constraintAnnotation.min();
    }


}
