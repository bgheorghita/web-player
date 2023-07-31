package dev.gb.webplayerauthorizationserver.services.roles;

import dev.gb.webplayerauthorizationserver.models.users.Role;

public interface RoleService {
    Role findByName(String name);
    void save(Role role);
    void remove(String roleName);
}
