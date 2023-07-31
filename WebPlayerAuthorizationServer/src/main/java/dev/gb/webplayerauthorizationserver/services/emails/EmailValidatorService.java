package dev.gb.webplayerauthorizationserver.services.emails;

public interface EmailValidatorService {
    void confirm(String email);
    void deactivate(String email);
}
