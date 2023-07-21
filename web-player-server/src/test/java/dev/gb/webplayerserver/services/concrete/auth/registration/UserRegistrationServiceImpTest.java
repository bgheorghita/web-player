package dev.gb.webplayerserver.services.concrete.auth.registration;

import dev.gb.webplayerserver.exceptions.EmailAlreadyExistsException;
import dev.gb.webplayerserver.exceptions.InvalidPasswordException;
import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.models.users.UserType;
import dev.gb.webplayerserver.services.concrete.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRegistrationServiceImpTest {
    private UserRegistrationServiceImp userRegistrationServiceImp;
    private final  UserService userService = mock(UserService.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private User user;

    @BeforeEach
    void setUp() {
        userRegistrationServiceImp = new UserRegistrationServiceImp(userService, passwordEncoder);
        user = new User()
                .withUsername("username")
                .withUserType(UserType.REGULAR)
                .withEmail("email@example.com")
                .withPassword("asdhk23^5634@#$sdA")
                .buildUser();
    }

    @Test
    void registerUser_ShouldThrowEmailAlreadyExistsException_WhenItIsRegistered(){
        String userEmail = user.getEmail();
        when(userService.findByEmail(userEmail)).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class,
                () -> userRegistrationServiceImp.registerUser(user));
    }

    @Test
    void registerUser_ShouldSaveUser_WhenEmailIsNotRegistered(){
        String userEmail = user.getEmail();
        when(userService.findByEmail(userEmail)).thenReturn(Optional.empty());
        when(userService.save(user)).thenReturn(user);

        userRegistrationServiceImp.registerUser(user);

        verify(userService, times(1)).findByEmail(userEmail);
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowInvalidPasswordException_WhenPasswordIsNotStrong(){
        String userEmail = user.getEmail();
        user.setPassword("1234");
        when(userService.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordException.class,
                () -> userRegistrationServiceImp.registerUser(user));

        verify(userService, times(1)).findByEmail(userEmail);
    }
}