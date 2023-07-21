package dev.gb.webplayerserver.services.concrete.auth.registration;

import dev.gb.webplayerserver.exceptions.EmailAlreadyExistsException;
import dev.gb.webplayerserver.exceptions.InvalidPasswordException;
import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.concrete.users.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationServiceImp implements UserRegistrationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationServiceImp(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void registerUser(User user) {
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email '" + user.getEmail() + "' is already registered");
        }
        setEncodedPassword(user);
        userService.save(user);
    }

    private void setEncodedPassword(User user) {
        String rawPassword = user.getPassword();
        validatePassword(rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

    private void validatePassword(String rawPassword){
        final String EMAIL_CONSTRAINT_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
        if(!rawPassword.matches(EMAIL_CONSTRAINT_REGEX)){
            throw new InvalidPasswordException("Password should contain at least one digit, one letter, " +
                    "one special character, and be at least 8 characters long");
        }
    }
}