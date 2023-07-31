package dev.gb.webplayerauthorizationserver.exceptions;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
