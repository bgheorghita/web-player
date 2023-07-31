package dev.gb.webplayerauthorizationserver.exceptions;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String msg){
        super(msg);
    }
}
