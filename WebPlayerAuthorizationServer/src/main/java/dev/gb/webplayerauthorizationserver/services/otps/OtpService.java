package dev.gb.webplayerauthorizationserver.services.otps;

import dev.gb.webplayerauthorizationserver.models.otps.Otp;

import java.util.Optional;

public interface OtpService {
    Optional<Otp> findByUserEmail(String emailIdentifier);
    Otp generateOtp(String emailIdentifier);
    boolean validateOtp(String emailIdentifier, String codeToValidate);
    void refreshOtp(String emailIdentifier);
}
