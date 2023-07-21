package dev.gb.webplayerserver.services.concrete.users;

import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.repositories.users.UserRepository;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp extends CrudServiceImp<User, Long> implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
