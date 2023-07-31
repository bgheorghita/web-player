package dev.gb.webplayerauthorizationserver.services.otps;

import dev.gb.webplayerauthorizationserver.models.otps.Otp;
import dev.gb.webplayerauthorizationserver.repositories.OtpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
class OtpServiceImpTest {
    private OtpServiceImp otpServiceImp;
    private final OtpRepository otpRepositoryMock = mock(OtpRepository.class);
    private Otp mockOtp;


    @BeforeEach
    void setUp() {
        otpServiceImp = new OtpServiceImp(otpRepositoryMock);

        mockOtp = new Otp();
        String email = "user@example.com";
        String code = "123456";
        mockOtp.setCode(code);
        mockOtp.setUserEmail(email);
        mockOtp.setGeneratedAt(LocalDateTime.now());
    }

    @Test
    void generateOtp_ShouldSaveTheGeneratedOtp(){
        String userEmail = mockOtp.getUserEmail();
        when(otpRepositoryMock.save(mockOtp)).thenReturn(mockOtp);

        otpServiceImp.generateOtp(userEmail);

        verify(otpRepositoryMock, times(1)).save(Mockito.any(Otp.class));
    }

    @Test
    void validateOtp_ShouldReturnTrue_WhenOtpIsValid(){
        String userEmail = mockOtp.getUserEmail();
        String userCode = mockOtp.getCode();
        when(otpRepositoryMock.findByUserEmail(userEmail)).thenReturn(Optional.of(mockOtp));

        ReflectionTestUtils.setField(otpServiceImp, "otpExpiresInSeconds", 300);
        boolean result = otpServiceImp.validateOtp(userEmail, userCode);

        assertTrue(result);
        verify(otpRepositoryMock, times(1)).findByUserEmail(userEmail);
    }

    @Test
    void validateOtp_ShouldReturnFalse_WhenOtpIsNotValid(){
        String userEmail = mockOtp.getUserEmail();
        when(otpRepositoryMock.findByUserEmail(userEmail)).thenReturn(Optional.of(mockOtp));

        boolean result = otpServiceImp.validateOtp(userEmail, "INVALID_OTP");

        assertFalse(result);
        verify(otpRepositoryMock, times(1)).findByUserEmail(userEmail);
    }

    @Test
    void validateOtp_ShouldReturnFalse_WhenOtpToValidateDoesNotExist(){
        String userEmail = "user@example.com";
        when(otpRepositoryMock.findByUserEmail(userEmail)).thenReturn(Optional.empty());

        boolean result = otpServiceImp.validateOtp(userEmail, "OTP_DOES_NOT_EXIST");

        assertFalse(result);
        verify(otpRepositoryMock, times(1)).findByUserEmail(userEmail);

    }

    @Test
    void refreshOtp_ShouldRegenerateOtp_WhenThereIsAnOldOtpForThatUser(){
        String userEmail = mockOtp.getUserEmail();
        when(otpRepositoryMock.findByUserEmail(userEmail)).thenReturn(Optional.of(mockOtp));
        when(otpRepositoryMock.save(mockOtp)).thenReturn(mockOtp);

        otpServiceImp.refreshOtp(userEmail);

        verify(otpRepositoryMock, times(1)).findByUserEmail(userEmail);
        verify(otpRepositoryMock, times(1)).save(Mockito.any(Otp.class));
    }

    @Test
    void refreshOtp_ShouldNotRegenerateOtp_WhenThereIsNotAnOldOtpForThatUser(){
        String userEmail = mockOtp.getUserEmail();
        when(otpRepositoryMock.findByUserEmail(userEmail)).thenReturn(Optional.empty());
        when(otpRepositoryMock.save(mockOtp)).thenReturn(mockOtp);

        otpServiceImp.refreshOtp(userEmail);

        verify(otpRepositoryMock, times(1)).findByUserEmail(userEmail);
        verify(otpRepositoryMock, times(0)).save(Mockito.any(Otp.class));
    }
}