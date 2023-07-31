package dev.gb.webplayerauthorizationserver.models.users.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
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
