package dev.gb.webplayerserver.models.validators;

import dev.gb.webplayerserver.models.users.UserType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class UserTypeValidator implements ConstraintValidator<ValidUserType, UserType> {
    @Override
    public void initialize(ValidUserType constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserType value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(UserType.values()).contains(value);
    }
}