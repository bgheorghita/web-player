package dev.gb.webplayerauthorizationserver.services.users;

import dev.gb.webplayerauthorizationserver.exceptions.EmailAddressAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.exceptions.InvalidPasswordException;
import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.exceptions.UsernameAlreadyExistsException;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.repositories.UserRepository;
import dev.gb.webplayerauthorizationserver.utils.ConstraintsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserServiceImpl userServiceUnderTest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserServiceImpl(userRepository, passwordEncoder);
        mockUser = new User();
    }

    @Test
    void findByEmail_ShouldReturnThrowResourceNotFoundException_WhenUserDoesNotExistWithThatEmail(){
        String mockEmail = "mock@email.com";
        when(userRepository.findByEmail(mockEmail)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userServiceUnderTest.findByEmail(mockEmail));
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExistsWithThatEmail(){
        mockUser.setEmail("mock@email.com");
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        User user = userServiceUnderTest.findByEmail(mockUser.getEmail());

        assertNotNull(user);
    }

    @Test
    void registerUser_ShouldThrowEmailAddressAlreadyExistsException(){
        mockUser.setEmail("mock@email.com");
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(EmailAddressAlreadyExistsException.class, () -> userServiceUnderTest.registerUser(mockUser));
    }

    @Test
    void registerUser_ShouldThrowUsernameAlreadyExistsException(){
        mockUser.setEmail("mock@email.com");
        mockUser.setUsername("mockUsername");
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> userServiceUnderTest.registerUser(mockUser));
    }

    @Test
    void registerUser_ShouldThrowInvalidPasswordException_WhenPasswordIsTooSimple(){
        mockUser.setEmail("mock@email.com");
        mockUser.setUsername("mockUsername");
        mockUser.setPassword("1234");
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordException.class, () -> userServiceUnderTest.registerUser(mockUser));
    }

    @Test
    void registerUser_ShouldEncodeThePassword_BeforeRegisteringUser(){
        mockUser.setEmail("mock@email.com");
        mockUser.setUsername("mockUsername");
        mockUser.setPassword("1234");

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(mockUser.getPassword())).thenReturn("encodedPasswordMock");
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        try (var constraintsMock = mockStatic(ConstraintsUtil.class)) {
            constraintsMock.when(() -> ConstraintsUtil.validatePassword(mockUser.getPassword())).thenReturn(true);

            userServiceUnderTest.registerUser(mockUser);

            verify(passwordEncoder, times(1)).encode(anyString());
            verify(userRepository, times(1)).save(mockUser);
        }
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException(){
        mockUser.setUsername("mockUsername");
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceUnderTest.loadUserByUsername(mockUser.getUsername()));
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenSuccessfullyFound() {
        mockUser.setUsername("mockUsername");
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userServiceUnderTest.loadUserByUsername(mockUser.getUsername());
        assertNotNull(userDetails);
    }
}