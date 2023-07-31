package dev.gb.webplayerauthorizationserver.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String msg){
        super(msg);
    }
}
