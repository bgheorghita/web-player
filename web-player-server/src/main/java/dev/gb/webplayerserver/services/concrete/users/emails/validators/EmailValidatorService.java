package dev.gb.webplayerserver.services.concrete.users.emails.validators;

public interface EmailValidatorService {
    void confirm(String email);
    void deactivate(String email);
}
