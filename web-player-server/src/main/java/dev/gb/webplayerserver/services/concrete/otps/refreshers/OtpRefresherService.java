package dev.gb.webplayerserver.services.concrete.otps.refreshers;

import dev.gb.webplayerserver.models.users.User;

public interface OtpRefresherService {
    void refreshOtp(User user);
}
