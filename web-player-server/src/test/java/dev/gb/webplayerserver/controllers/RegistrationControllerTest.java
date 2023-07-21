package dev.gb.webplayerserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gb.webplayerserver.dtos.UserDto;
import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.models.users.UserType;
import dev.gb.webplayerserver.services.concrete.auth.registration.UserRegistrationService;
import dev.gb.webplayerserver.services.concrete.otps.senders.EmailOtpSenderService;
import dev.gb.webplayerserver.services.concrete.users.emails.validators.EmailValidatorManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRegistrationService userRegistrationService = mock(UserRegistrationService.class);
    private final EmailOtpSenderService emailOtpSenderService = mock(EmailOtpSenderService.class);
    private final EmailValidatorManager emailValidatorManager = mock(EmailValidatorManager.class);
    private MockMvc mockMvc;
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        registrationController = new RegistrationController(
                userRegistrationService,
                emailOtpSenderService,
                emailValidatorManager
        );
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void register_ShouldReturnCreatedStatus() throws Exception {
        UserDto userDto = new UserDto()
                .withEmail("user@email.com")
                .withPassword("askfaFAFa#$@$^4345")
                .withUsername("username")
                .withUserType(UserType.REGULAR)
                .buildUserDto();

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        verify(userRegistrationService, times(1)).registerUser(any(User.class));
    }

    @Test
    void verifyEmail_ShouldReturnOkStatus() throws Exception {
        String email = "user@example.com";

        mockMvc.perform(post("/api/v1/register/verify-email")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(emailOtpSenderService, times(1)).sendOtp(email);
    }

    @Test
    void confirmEmail_ShouldReturnOkStatus() throws Exception {
        String email = "user@example.com";
        String code = "123456";

        mockMvc.perform(post("/api/v1/register/confirm-email")
                        .param("email", email)
                        .param("code", code))
                .andExpect(status().isOk());

        verify(emailValidatorManager, times(1)).validateAndConfirmUserEmail(code, email);
    }
}