package dev.gb.webplayerauthorizationserver.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManageRoleRequest {
    private String userEmailIdentifier;
    private String roleNameIdentifier;
}
