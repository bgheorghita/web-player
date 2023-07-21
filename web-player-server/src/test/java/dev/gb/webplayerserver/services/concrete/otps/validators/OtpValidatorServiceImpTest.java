package dev.gb.webplayerserver.services.concrete.otps.validators;

import dev.gb.webplayerserver.exceptions.InvalidOtpException;
import dev.gb.webplayerserver.services.concrete.otps.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OtpValidatorServiceImpTest {
    private OtpValidatorServiceImp otpValidatorServiceImp;
    private OtpService otpService = mock(OtpService.class);
    @BeforeEach
    void setUp() {
        otpValidatorServiceImp = new OtpValidatorServiceImp(otpService);
    }

    @Test
    void validate_ShouldThrowInvalidOtpException_WhenOtpIsNotValid(){
        String codeToValidate = "codeToValidate";
        String userEmail = "email@example.com";

        when(otpService.validateOtp(codeToValidate, userEmail)).thenReturn(false);
        assertThrows(InvalidOtpException.class, () -> otpValidatorServiceImp.validate(codeToValidate, userEmail));
    }
}