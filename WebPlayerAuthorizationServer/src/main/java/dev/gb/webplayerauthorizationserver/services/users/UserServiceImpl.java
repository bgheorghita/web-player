package dev.gb.webplayerauthorizationserver.services.users;

import dev.gb.webplayerauthorizationserver.exceptions.EmailAddressAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.exceptions.InvalidPasswordException;
import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.exceptions.UsernameAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.models.users.SecurityUser;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.repositories.UserRepository;
import dev.gb.webplayerauthorizationserver.utils.ConstraintsUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email '" + email + "' has not been found"));
    }

    @Override
    public void registerUser(User user) {
        String emailAddress = user.getEmail();
        checkIfEmailExists(emailAddress);

        String username = user.getUsername();
        checkIfUsernameExists(username);

        String rawPassword = user.getPassword();
        validatePassword(rawPassword);

        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    private void checkIfUsernameExists(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists.");
        });
    }

    private void checkIfEmailExists(String emailAddress) {
        userRepository.findByEmail(emailAddress).ifPresent(email -> {
            throw new EmailAddressAlreadyExistsException("Email address '" + emailAddress + "' already exists.");
        });
    }

    private void validatePassword(String rawPassword) {
        if(!ConstraintsUtil.validatePassword(rawPassword)){
            throw new InvalidPasswordException("Password should contain at least one digit, one letter, " +
                    "one special character, and be at least 8 characters long");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' has not been found."));

        return new SecurityUser(user);
    }
}
