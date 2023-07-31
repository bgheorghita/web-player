package dev.gb.webplayerauthorizationserver.services.emails;

import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class EmailValidatorServiceImpTest {
    private EmailValidatorServiceImp emailValidatorServiceImp;
    private final UserService userService = mock(UserService.class);
    private User user;
    @BeforeEach
    void setUp() {
        emailValidatorServiceImp = new EmailValidatorServiceImp(userService);

        user = new User();
        user.setEmail("email@example.com");
        user.setEmailVerified(false);
    }

    @Test
    void confirm_ShouldConfirmUserEmail_WhenUserWithThatEmailIsSaved(){
        String userEmail = user.getEmail();
        when(userService.findByEmail(userEmail)).thenReturn(user);

        emailValidatorServiceImp.confirm(userEmail);

        assertTrue(user.isEmailVerified());
        verify(userService, times(1)).findByEmail(userEmail);
    }

    @Test
    void deactivate_ShouldDeactivateUserEmail_WhenUserWithThatEmailIsSaved(){
        String userEmail = user.getEmail();
        user.setEmailVerified(true);
        when(userService.findByEmail(userEmail)).thenReturn(user);

        emailValidatorServiceImp.deactivate(userEmail);

        assertFalse(user.isEmailVerified());
        verify(userService, times(1)).findByEmail(userEmail);
    }
}