package dev.gb.webplayerserver.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
