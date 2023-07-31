package dev.gb.webplayerauthorizationserver.services.roles;

import dev.gb.webplayerauthorizationserver.models.users.Role;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class RoleManagerServiceImplTest {

    private final UserService userService = mock(UserService.class);
    private final RoleService roleService = mock(RoleService.class);
    private User mockUser;
    private Role firstMockRole;
    private Role secondMockRole;
    private RoleManagerService roleManagerServiceUnderTest;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setEmail("mock@email.com");

        firstMockRole = new Role();
        firstMockRole.setName("FIRST_MOCK_ROLE");

        secondMockRole = new Role();
        secondMockRole.setName("SECOND_MOCK_ROLE");

        roleManagerServiceUnderTest = new RoleManagerServiceImpl(userService, roleService);
    }

    @Test
    void assignRole_ShouldAssignTheSpecifiedRoleToTheSpecifiedUser_WhenBothRoleAndUserArePresent(){
        when(userService.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
        when(roleService.findByName(firstMockRole.getName())).thenReturn(firstMockRole);

        roleManagerServiceUnderTest.assignRole(firstMockRole.getName(), mockUser.getEmail());

        verify(userService, times(1)).findByEmail(mockUser.getEmail());
        verify(roleService, times(1)).findByName(firstMockRole.getName());
        assertTrue(mockUser.getRoleSet().contains(firstMockRole));
    }

    @Test
    void assignRole_ShouldAssignMultipleRolesToTheSpecifiedUser_WhenBothRolesAndUserArePresent(){
        when(userService.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
        when(roleService.findByName(firstMockRole.getName())).thenReturn(firstMockRole);
        when(roleService.findByName(secondMockRole.getName())).thenReturn(secondMockRole);

        roleManagerServiceUnderTest.assignRole(firstMockRole.getName(), mockUser.getEmail());
        roleManagerServiceUnderTest.assignRole(secondMockRole.getName(), mockUser.getEmail());

        verify(userService, times(2)).findByEmail(mockUser.getEmail());
        verify(roleService, times(1)).findByName(firstMockRole.getName());
        verify(roleService, times(1)).findByName(secondMockRole.getName());
        assertTrue(mockUser.getRoleSet().contains(firstMockRole));
        assertTrue(mockUser.getRoleSet().contains(secondMockRole));
    }

    @Test
    void revokeRole_ShouldRemoveTheSpecifiedRoleFromTheSpecifiedUser_WhenBothRoleAndUserArePresent(){
        mockUser.getRoleSet().add(firstMockRole);

        when(userService.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
        when(roleService.findByName(firstMockRole.getName())).thenReturn(firstMockRole);

        roleManagerServiceUnderTest.revokeRole(firstMockRole.getName(), mockUser.getEmail());

        verify(userService, times(1)).findByEmail(mockUser.getEmail());
        verify(roleService, times(1)).findByName(firstMockRole.getName());
        assertFalse(mockUser.getRoleSet().contains(firstMockRole));
    }
}