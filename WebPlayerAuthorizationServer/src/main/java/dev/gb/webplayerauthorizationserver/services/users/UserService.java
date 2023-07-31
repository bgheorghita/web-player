package dev.gb.webplayerauthorizationserver.services.users;

import dev.gb.webplayerauthorizationserver.models.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    void registerUser(User user);
}
