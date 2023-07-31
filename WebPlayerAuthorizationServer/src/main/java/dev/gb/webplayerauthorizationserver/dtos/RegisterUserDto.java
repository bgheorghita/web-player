package dev.gb.webplayerauthorizationserver.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String username;
    private String password;
    private String userType;
}
