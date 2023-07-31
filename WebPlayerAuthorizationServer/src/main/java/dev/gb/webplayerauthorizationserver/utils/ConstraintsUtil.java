package dev.gb.webplayerauthorizationserver.utils;

public class ConstraintsUtil {
    public static final String PASSWORD_CONSTRAINT_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";

    public static boolean validatePassword(String rawPassword){
        return rawPassword.matches(PASSWORD_CONSTRAINT_REGEX);
    }
}
