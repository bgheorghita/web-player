package dev.gb.webplayerauthorizationserver.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg){
        super(msg);
    }
}
