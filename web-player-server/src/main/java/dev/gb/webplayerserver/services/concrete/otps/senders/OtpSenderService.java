package dev.gb.webplayerserver.services.concrete.otps.senders;

public interface OtpSenderService {
    void sendOtp(String emailIdentifier);
}
