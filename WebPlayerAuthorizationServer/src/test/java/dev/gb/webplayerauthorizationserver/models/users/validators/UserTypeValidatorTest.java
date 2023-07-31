package dev.gb.webplayerauthorizationserver.models.users.validators;

import dev.gb.webplayerauthorizationserver.models.users.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserTypeValidatorTest {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    UserTypeValidator userTypeValidator;

    @Test
    void isValid_ShouldReturnTrue_WhenValueIsValid(){
        UserType validUserType = UserType.PREMIUM;

        boolean result = userTypeValidator.isValid(validUserType.name(), constraintValidatorContext);

        assertTrue(result);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenValueIsNotValid(){
        String invalidUserType = "INVALID_USER_TYPE";

        boolean result = userTypeValidator.isValid(invalidUserType, constraintValidatorContext);

        assertFalse(result);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenValueIsNULL(){
        boolean result = userTypeValidator.isValid(null, constraintValidatorContext);
        assertFalse(result);
    }
}