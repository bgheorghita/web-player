package dev.gb.webplayerauthorizationserver.services.otps.senders;

public interface OtpSenderService {
    void sendOtp(String emailIdentifier);
}
