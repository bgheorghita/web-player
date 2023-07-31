package dev.gb.webplayerauthorizationserver.services.roles;

import dev.gb.webplayerauthorizationserver.models.users.Role;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class RoleManagerServiceImpl implements RoleManagerService {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void assignRole(String roleNameIdentifier, String userEmailIdentifier) {
        User user = userService.findByEmail(userEmailIdentifier);
        Role role = roleService.findByName(roleNameIdentifier);
        user.getRoleSet().add(role);
    }

    @Override
    public void revokeRole(String roleNameIdentifier, String userEmailIdentifier) {
        User user = userService.findByEmail(userEmailIdentifier);
        Role role = roleService.findByName(roleNameIdentifier);
        user.getRoleSet().remove(role);
    }
}
