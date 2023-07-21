package dev.gb.webplayerserver.services.concrete.auth.registration;

import dev.gb.webplayerserver.models.users.User;

public interface UserRegistrationService {
    void registerUser(User user);
}
