package dev.gb.webplayerauthorizationserver.exceptions;

public class RoleNameAlreadyExistsException extends BadRequestException {
    public RoleNameAlreadyExistsException(String msg) {
        super(msg);
    }
}
