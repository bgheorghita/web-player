package dev.gb.webplayerauthorizationserver.controllers;

import dev.gb.webplayerauthorizationserver.dtos.RegisterUserDto;
import dev.gb.webplayerauthorizationserver.mappers.dtos.impl.RegisterUserToUserMapper;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.services.emails.managers.EmailValidatorManager;
import dev.gb.webplayerauthorizationserver.services.otps.senders.managers.OtpSenderManagerService;
import dev.gb.webplayerauthorizationserver.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final OtpSenderManagerService otpSenderManagerService;
    private final EmailValidatorManager emailValidatorManager;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterUserDto registerUserDto){
        User user = RegisterUserToUserMapper.fromRegisterUserDto(registerUserDto);
        userService.registerUser(user);
    }

    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEmailAddress(@RequestParam("email") String email){
        otpSenderManagerService.sendOtp(email);
    }

    @PostMapping("/confirm-email")
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@RequestParam("email") String email, @RequestParam("code") String code){
        emailValidatorManager.validateEmailWithOtp(email, code);
    }

    @PostMapping("/invalidate-email")
    @ResponseStatus(HttpStatus.OK)
    public void invalidateEmail(@RequestParam("email") String email){
        emailValidatorManager.invalidateEmail(email);
    }
}
