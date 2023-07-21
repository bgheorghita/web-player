package dev.gb.webplayerserver.services.concrete.otps;

import dev.gb.webplayerserver.models.concrete.auth.Otp;

import java.util.Optional;

public interface OtpService {
    Optional<Otp> findByUserEmail(String emailIdentifier);
    Otp generateOtp(String emailIdentifier);
    boolean validateOtp(String codeToValidate, String emailIdentifier);
    void refreshOtp(String emailIdentifier);
}
