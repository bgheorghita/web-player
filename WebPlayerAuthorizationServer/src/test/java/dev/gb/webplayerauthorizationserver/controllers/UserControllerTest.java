package dev.gb.webplayerauthorizationserver.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.models.users.UserType;
import dev.gb.webplayerauthorizationserver.services.emails.managers.EmailValidatorManager;
import dev.gb.webplayerauthorizationserver.services.otps.senders.managers.OtpSenderManagerService;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private OtpSenderManagerService otpSenderManagerService;
    @MockBean
    private EmailValidatorManager emailValidatorManager;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("usernameee");
        user.setUserType(UserType.REGULAR);
        user.setEmail("email@example.com");
        user.setPassword("asdhk23^5634@#$sdA");
    }

    @Test
    void registerUser_Successfully() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).registerUser(user);
    }

    @Test
    void verifyEmailAddress_Successfully() throws Exception {
        mockMvc.perform(post("/users/verify-email")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk());

        verify(otpSenderManagerService, times(1)).sendOtp(user.getEmail());
    }

    @Test
    void confirmEmail_Successfully() throws Exception {
        String mockOtp = "1234";
        String mockEmail = user.getEmail();

        mockMvc.perform(post("/users/confirm-email")
                        .param("email", mockEmail)
                        .param("code", mockOtp))
                .andExpect(status().isOk());

        verify(emailValidatorManager, times(1)).validateEmailWithOtp(mockEmail, mockOtp);
    }

    @Test
    @Disabled
    void invalidateEmail_ShouldRedirectToLogin_WhenNotAuthenticated() throws Exception {
        String mockEmail = user.getEmail();

        mockMvc.perform(post("/users/invalidate-email")
                        .param("email", mockEmail))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrlPattern("**/login"));

        verify(emailValidatorManager, times(0)).invalidateEmail(mockEmail);
    }
}