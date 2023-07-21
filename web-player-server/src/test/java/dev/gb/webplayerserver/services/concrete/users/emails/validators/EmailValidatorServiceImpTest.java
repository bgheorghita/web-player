package dev.gb.webplayerserver.services.concrete.users.emails.validators;

import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.concrete.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailValidatorServiceImpTest {
    private EmailValidatorServiceImp emailValidatorServiceImp;
    private final UserService userService = mock(UserService.class);
    private User user;
    @BeforeEach
    void setUp() {
        emailValidatorServiceImp = new EmailValidatorServiceImp(userService);

        user = new User()
                .withEmail("email@example.com")
                .withEmailVerified(false)
                .buildUser();
    }

    @Test
    void confirm_ShouldConfirmUserEmail_WhenUserWithThatEmailIsSaved(){
        String userEmail = user.getEmail();
        when(userService.findByEmail(userEmail)).thenReturn(Optional.of(user));

        emailValidatorServiceImp.confirm(userEmail);

        assertTrue(user.isEmailVerified());
        verify(userService, times(1)).findByEmail(userEmail);
    }

    @Test
    void deactivate_ShouldDeactivateUserEmail_WhenUserWithThatEmailIsSaved(){
        String userEmail = user.getEmail();
        user.setEmailVerified(true);
        when(userService.findByEmail(userEmail)).thenReturn(Optional.of(user));

        emailValidatorServiceImp.deactivate(userEmail);

        assertFalse(user.isEmailVerified());
        verify(userService, times(1)).findByEmail(userEmail);
    }
}