package dev.gb.webplayerserver.services.concrete.users.emails.validators;

import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.concrete.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class EmailValidatorServiceImp implements EmailValidatorService {
    private final UserService userService;

    public EmailValidatorServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void confirm(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmailVerified(true);
        }
    }

    @Override
    public void deactivate(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmailVerified(false);
        }
    }
}
