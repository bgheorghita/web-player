package dev.gb.webplayerauthorizationserver.services.otps.validators;

public interface OtpValidatorService {
    void validate(String userEmail, String code);
}
