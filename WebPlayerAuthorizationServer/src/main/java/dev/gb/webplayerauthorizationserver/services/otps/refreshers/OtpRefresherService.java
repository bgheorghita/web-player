package dev.gb.webplayerauthorizationserver.services.otps.refreshers;


import dev.gb.webplayerauthorizationserver.models.users.User;

public interface OtpRefresherService {
    void refreshOtp(User user);
}
