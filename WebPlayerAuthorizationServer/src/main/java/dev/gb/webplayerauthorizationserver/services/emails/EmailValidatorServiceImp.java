package dev.gb.webplayerauthorizationserver.services.emails;

import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EmailValidatorServiceImp implements EmailValidatorService {
    private final UserService userService;

    public EmailValidatorServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void confirm(String email) {
        User user = userService.findByEmail(email);
        user.setEmailVerified(true);
    }

    @Override
    public void deactivate(String email) {
        User user = userService.findByEmail(email);
        user.setEmailVerified(false);
    }
}
