package com.tapia.user.validation;

import org.springframework.beans.factory.annotation.Value;
import javax.validation.ConstraintValidator;


public class KeyValidator implements ConstraintValidator<ClaveValida, String> {

    @Value("${validation.key.pattern}")
    private String pattern;

    @Override
    public void initialize(ClaveValida constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        return value.matches(pattern);
    }
}