package dev.gb.webplayerserver.services.concrete.users;

import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.base.crud.CrudService;

import java.util.Optional;

public interface UserService extends CrudService<User, Long> {
    Optional<User> findByEmail(String email);
}
