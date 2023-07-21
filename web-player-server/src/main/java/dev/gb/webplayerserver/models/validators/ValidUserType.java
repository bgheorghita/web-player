package dev.gb.webplayerserver.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserTypeValidator.class)
@Documented
public @interface ValidUserType {
    String message() default "User type is missing or invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
