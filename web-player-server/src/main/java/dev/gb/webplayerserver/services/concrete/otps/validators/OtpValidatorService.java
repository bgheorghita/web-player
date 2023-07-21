package dev.gb.webplayerserver.services.concrete.otps.validators;

public interface OtpValidatorService {
    void validate(String code, String userEmail);
}
