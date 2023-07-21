package dev.gb.webplayerserver.services.concrete.otps.refreshers;

import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.concrete.otps.OtpService;
import org.springframework.stereotype.Service;

@Service
public class OtpRefresherServiceImp implements OtpRefresherService {
    private final OtpService otpService;

    public OtpRefresherServiceImp(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void refreshOtp(User user) {
        otpService.refreshOtp(user.getEmail());
    }
}
