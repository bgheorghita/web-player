package dev.gb.webplayerserver.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String msg){
        super(msg);
    }
}
