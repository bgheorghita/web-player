package dev.gb.webplayerauthorizationserver.services.emails.managers;

import dev.gb.webplayerauthorizationserver.services.emails.EmailValidatorService;
import dev.gb.webplayerauthorizationserver.services.otps.validators.OtpValidatorService;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorManagerImpl implements EmailValidatorManager {
    private final EmailValidatorService emailValidatorService;
    private final OtpValidatorService otpValidatorService;

    public EmailValidatorManagerImpl(EmailValidatorService emailValidatorService,
                                     OtpValidatorService otpValidatorService) {
        this.emailValidatorService = emailValidatorService;
        this.otpValidatorService = otpValidatorService;
    }

    @Override
    public void validateEmailWithOtp(String userEmail, String code) {
        otpValidatorService.validate(userEmail, code);
        emailValidatorService.confirm(userEmail);
    }

    @Override
    public void invalidateEmail(String userEmail) {
        emailValidatorService.deactivate(userEmail);
    }
}
