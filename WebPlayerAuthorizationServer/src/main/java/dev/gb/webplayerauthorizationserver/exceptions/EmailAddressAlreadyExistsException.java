package dev.gb.webplayerauthorizationserver.exceptions;

public class EmailAddressAlreadyExistsException extends BadRequestException {
    public EmailAddressAlreadyExistsException(String msg){
        super(msg);
    }
}
