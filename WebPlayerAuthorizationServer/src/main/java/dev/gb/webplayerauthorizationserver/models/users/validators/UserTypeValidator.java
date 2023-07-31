package dev.gb.webplayerauthorizationserver.models.users.validators;


import dev.gb.webplayerauthorizationserver.models.users.UserType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserTypeValidator implements ConstraintValidator<ValidUserType, String> {
    @Override
    public void initialize(ValidUserType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }

        try{
            UserType.valueOf(value);
        } catch (IllegalArgumentException e){
            return false;
        }

        return true;
    }
}