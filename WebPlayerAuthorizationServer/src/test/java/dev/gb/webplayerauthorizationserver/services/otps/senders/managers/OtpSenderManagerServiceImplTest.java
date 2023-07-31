package dev.gb.webplayerauthorizationserver.services.otps.senders.managers;

import dev.gb.webplayerauthorizationserver.exceptions.ServiceUnavailableException;
import dev.gb.webplayerauthorizationserver.services.otps.senders.OtpSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class OtpSenderManagerServiceImplTest {
    private final OtpSenderService otpSenderService = mock(OtpSenderService.class);
    private OtpSenderManagerServiceImpl otpSenderManagerServiceUnderTest;

    @BeforeEach
    void setUp() {
        otpSenderManagerServiceUnderTest = new OtpSenderManagerServiceImpl(otpSenderService);
    }

    @Test
    void sendOtp_ShouldThrowServiceUnavailableException_WhenOtpServiceIsDeactivated(){
        String mockOtp = "12345";
        ReflectionTestUtils.setField(otpSenderManagerServiceUnderTest, "isOtpServiceActivated", false);
        assertThrows(ServiceUnavailableException.class, () -> otpSenderManagerServiceUnderTest.sendOtp(mockOtp));
    }

    @Test
    void sendOtp_ShouldSendOtp_WhenOtpServiceIsActivated(){
        String mockEmail = "mockEmail@email.com";
        ReflectionTestUtils.setField(otpSenderManagerServiceUnderTest, "isOtpServiceActivated", true);
        doNothing().when(otpSenderService).sendOtp(mockEmail);

        otpSenderManagerServiceUnderTest.sendOtp(mockEmail);

        verify(otpSenderService, times(1)).sendOtp(mockEmail);
    }
}