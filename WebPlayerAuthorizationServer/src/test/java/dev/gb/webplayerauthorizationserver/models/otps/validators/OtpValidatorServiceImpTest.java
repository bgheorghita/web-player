package dev.gb.webplayerauthorizationserver.models.otps.validators;

import dev.gb.webplayerauthorizationserver.exceptions.InvalidOtpException;
import dev.gb.webplayerauthorizationserver.services.otps.OtpService;
import dev.gb.webplayerauthorizationserver.services.otps.validators.OtpValidatorServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class OtpValidatorServiceImpTest {
    private OtpValidatorServiceImp otpValidatorServiceImp;
    private final OtpService otpService = mock(OtpService.class);

    @BeforeEach
    void setUp() {
        otpValidatorServiceImp = new OtpValidatorServiceImp(otpService);
    }

    @Test
    void validate_ShouldThrowInvalidOtpException_WhenOtpIsNotValid(){
        String codeToValidate = "codeToValidate";
        String userEmail = "email@example.com";

        when(otpService.validateOtp(userEmail, codeToValidate)).thenReturn(false);
        assertThrows(InvalidOtpException.class, () -> otpValidatorServiceImp.validate(userEmail, codeToValidate));

        verify(otpService, times(1)).validateOtp(userEmail, codeToValidate);
    }
}