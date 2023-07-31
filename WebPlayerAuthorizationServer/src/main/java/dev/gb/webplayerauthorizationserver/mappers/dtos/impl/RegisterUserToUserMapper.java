package dev.gb.webplayerauthorizationserver.mappers.dtos.impl;

import dev.gb.webplayerauthorizationserver.dtos.RegisterUserDto;
import dev.gb.webplayerauthorizationserver.exceptions.BadRequestException;
import dev.gb.webplayerauthorizationserver.mappers.dtos.DtoToDomainMapper;
import dev.gb.webplayerauthorizationserver.models.users.User;
import dev.gb.webplayerauthorizationserver.models.users.UserType;

import java.util.Arrays;

public class RegisterUserToUserMapper implements DtoToDomainMapper<RegisterUserDto, User> {
    private final static RegisterUserToUserMapper instance = new RegisterUserToUserMapper();
    public RegisterUserToUserMapper(){}

    @Override
    public User fromDto(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setUserType(getUserType(registerUserDto.getUserType()));
        return user;
    }

    public static User fromRegisterUserDto(RegisterUserDto registerUserDto){
        return instance.fromDto(registerUserDto);
    }

    private UserType getUserType(String userType){
        UserType found;
        try{
            found = UserType.valueOf(userType);
        } catch (IllegalArgumentException e){
            throw new BadRequestException("User type '" + userType + "' does not exist. " +
                    "What exists: "+ Arrays.toString(UserType.values()));
        }
        return found;
    }
}
