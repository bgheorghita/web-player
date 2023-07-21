package dev.gb.webplayerserver.exceptions;

public class OtpNotFoundException extends RuntimeException {
    public OtpNotFoundException(String msg){
        super(msg);
    }
}
