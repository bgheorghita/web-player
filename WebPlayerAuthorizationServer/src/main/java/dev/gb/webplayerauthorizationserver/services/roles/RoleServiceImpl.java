package dev.gb.webplayerauthorizationserver.services.roles;

import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.exceptions.RoleNameAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.models.users.Role;
import dev.gb.webplayerauthorizationserver.repositories.RoleRepository;
import dev.gb.webplayerauthorizationserver.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role '" + name + "' has not been found"));
    }

    @Override
    public void save(Role role) {
        checkIfRoleNameExists(role.getName());
        roleRepository.save(role);
    }

    @Override
    public void remove(String roleName) {
        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role '" + roleName + "' has not been found."));

        removeRoleFromUsers(role);
        roleRepository.delete(role);
    }

    private void removeRoleFromUsers(Role role) {
        userRepository
                .findByRoleName(role.getName())
                .forEach(user -> user.getRoleSet().remove(role));
    }

    private void checkIfRoleNameExists(String name) {
        roleRepository.findByName(name).ifPresent(role -> {
            throw new RoleNameAlreadyExistsException("Role '" + name + "' already exists. It should be unique.");
        });
    }
}
