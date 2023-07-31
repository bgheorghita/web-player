package dev.gb.webplayerauthorizationserver.services.roles;

public interface RoleManagerService {
    void assignRole(String roleNameIdentifier, String userEmailIdentifier);
    void revokeRole(String roleNameIdentifier, String userEmailIdentifier);
}
