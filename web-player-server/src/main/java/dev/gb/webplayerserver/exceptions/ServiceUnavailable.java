package dev.gb.webplayerserver.exceptions;

public class ServiceUnavailable extends RuntimeException {
    public ServiceUnavailable(String msg){
        super(msg);
    }
}
