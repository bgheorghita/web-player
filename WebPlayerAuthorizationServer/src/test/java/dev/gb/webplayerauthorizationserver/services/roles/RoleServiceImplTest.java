package dev.gb.webplayerauthorizationserver.services.roles;

import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.exceptions.RoleNameAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.models.users.Role;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.repositories.RoleRepository;
import dev.gb.webplayerauthorizationserver.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class RoleServiceImplTest {

    private RoleService roleServiceUnderTest;
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        roleServiceUnderTest = new RoleServiceImpl(roleRepository, userRepository);
    }

    @Test
    void findByName_ShouldThrowResourceNotFoundException_IfNotPresent(){
        Role mockRole = new Role();
        mockRole.setName("mock_role");

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleServiceUnderTest.findByName(mockRole.getName()));
    }

    @Test
    void findByName_ShouldRetrieveRole_IfPresent(){
        Role mockRole = new Role();
        mockRole.setName("mock_role");

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.of(mockRole));

        Role role = roleServiceUnderTest.findByName(mockRole.getName());
        assertEquals(mockRole, role);
    }

    @Test
    void save_ShouldThrowRoleNameAlreadyExistsException_WhenAlreadySaved(){
        Role mockRole = new Role();
        mockRole.setName("ROLE_USER");

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.of(mockRole));

        assertThrows(RoleNameAlreadyExistsException.class, () -> roleServiceUnderTest.save(mockRole));
    }

    @Test
    void save_ShouldSaveRole_WhenRoleIsNotAlreadySaved(){
        Role mockRole = new Role();
        mockRole.setName("ROLE_USER");

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.empty());
        when(roleRepository.save(mockRole)).thenReturn(mockRole);

        roleServiceUnderTest.save(mockRole);

        verify(roleRepository, times(1)).findByName(mockRole.getName());
        verify(roleRepository, times(1)).save(mockRole);
    }

    @Test
    void remove_ShouldThrowResourceNotFoundException_WhenRoleNotPresent(){
        Role mockRole = new Role();
        mockRole.setName("MOCK_ROLE");

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleServiceUnderTest.remove(mockRole.getName()));
    }

    @Test
    void remove_ShouldDeleteRoleFromDatabase_AndRoleFromUsers_WhenPresent(){
        Role mockRole = new Role();
        mockRole.setName("MOCK_ROLE");

        User mockUser = new User();
        mockUser.getRoleSet().add(mockRole);

        when(roleRepository.findByName(mockRole.getName())).thenReturn(Optional.of(mockRole));
        when(userRepository.findByRoleName(mockRole.getName())).thenReturn(Set.of(mockUser));

        roleServiceUnderTest.remove(mockRole.getName());

        verify(roleRepository, times(1)).findByName(mockRole.getName());
        verify(userRepository, times(1)).findByRoleName(mockRole.getName());
        assertEquals(0, mockUser.getRoleSet().size());
    }
}