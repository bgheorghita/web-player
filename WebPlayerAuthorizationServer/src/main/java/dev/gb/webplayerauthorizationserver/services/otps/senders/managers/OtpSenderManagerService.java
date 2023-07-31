package dev.gb.webplayerauthorizationserver.services.otps.senders.managers;

public interface OtpSenderManagerService {
    void sendOtp(String emailIdentifier);
}
