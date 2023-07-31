package dev.gb.webplayerauthorizationserver.services.emails.managers;

public interface EmailValidatorManager {
    void validateEmailWithOtp(String userEmail, String code);
    void invalidateEmail(String userEmail);
}
