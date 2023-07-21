package dev.gb.webplayerserver.dtos;

import dev.gb.webplayerserver.models.users.UserType;
import dev.gb.webplayerserver.models.validators.ValidUserType;
import jakarta.validation.constraints.Email;

public class UserDto {
    @Email
    private String email;
    private String username;
    private String password;
    @ValidUserType
    private UserType userType;

    public UserDto(){}

    private UserDto(String email, String username, String password, UserType userType) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public UserDto withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserDto withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public UserDto buildUserDto() {
        return new UserDto(email, username, password, userType);
    }
}
