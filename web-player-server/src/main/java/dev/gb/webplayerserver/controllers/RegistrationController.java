package dev.gb.webplayerserver.controllers;

import dev.gb.webplayerserver.dtos.UserDto;
import dev.gb.webplayerserver.mappers.user.UserDtoMapper;
import dev.gb.webplayerserver.models.users.User;
import dev.gb.webplayerserver.services.concrete.auth.registration.UserRegistrationService;
import dev.gb.webplayerserver.services.concrete.otps.senders.EmailOtpSenderService;
import dev.gb.webplayerserver.services.concrete.users.emails.validators.EmailValidatorManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
    private final UserRegistrationService userRegistrationService;
    private final EmailOtpSenderService emailOtpSenderService;
    private final EmailValidatorManager emailValidatorManager;

    public RegistrationController(UserRegistrationService userRegistrationService,
                                  EmailOtpSenderService emailOtpSenderService,
                                  EmailValidatorManager emailValidatorManager) {
        this.userRegistrationService = userRegistrationService;
        this.emailOtpSenderService = emailOtpSenderService;
        this.emailValidatorManager = emailValidatorManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserDto userDto){
        User user = UserDtoMapper.fromDtoToUser(userDto);
        userRegistrationService.registerUser(user);
    }

    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailVerification(@RequestParam("email") String email){
        emailOtpSenderService.sendOtp(email);
    }

    @PostMapping("/confirm-email")
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@RequestParam("code") String code, @RequestParam("email") String email){
        emailValidatorManager.validateAndConfirmUserEmail(code, email);
    }
}
