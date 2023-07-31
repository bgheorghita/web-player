package dev.gb.webplayerauthorizationserver.exceptions;

public class UsernameAlreadyExistsException extends BadRequestException {
    public UsernameAlreadyExistsException(String msg){
        super(msg);
    }
}
