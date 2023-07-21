package dev.gb.webplayerserver.exceptions;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String msg){
        super(msg);
    }
}
