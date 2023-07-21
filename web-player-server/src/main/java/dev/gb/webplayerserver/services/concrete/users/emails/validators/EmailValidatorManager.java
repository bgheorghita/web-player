package dev.gb.webplayerserver.services.concrete.users.emails.validators;

import dev.gb.webplayerserver.services.concrete.otps.validators.OtpValidatorService;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorManager {
    private final EmailValidatorService emailValidatorService;
    private final OtpValidatorService otpValidatorService;

    public EmailValidatorManager(EmailValidatorService emailValidatorService, OtpValidatorService otpValidatorService) {
        this.emailValidatorService = emailValidatorService;
        this.otpValidatorService = otpValidatorService;
    }

    public void validateAndConfirmUserEmail(String code, String userEmail) {
        otpValidatorService.validate(code, userEmail);
        emailValidatorService.confirm(userEmail);
    }
}
