package dev.gb.webplayerauthorizationserver.controllers;

import dev.gb.webplayerauthorizationserver.api.models.ManageRoleRequest;
import dev.gb.webplayerauthorizationserver.dtos.RoleDto;
import dev.gb.webplayerauthorizationserver.mappers.dtos.impl.RoleDtoToRole;
import dev.gb.webplayerauthorizationserver.models.users.Role;
import dev.gb.webplayerauthorizationserver.services.roles.RoleManagerService;
import dev.gb.webplayerauthorizationserver.services.roles.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleManagerService roleManagerService;
    private final RoleService roleService;

    @PostMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    public void assignRole(@RequestBody ManageRoleRequest manageRoleRequest){
        String roleNameIdentifier = manageRoleRequest.getRoleNameIdentifier();
        String userEmailIdentifier = manageRoleRequest.getUserEmailIdentifier();
        roleManagerService.assignRole(roleNameIdentifier, userEmailIdentifier);
    }

    @PostMapping("/revoke")
    @ResponseStatus(HttpStatus.OK)
    public void revokeRole(@RequestBody ManageRoleRequest manageRoleRequest){
        String roleNameIdentifier = manageRoleRequest.getRoleNameIdentifier();
        String userEmailIdentifier = manageRoleRequest.getUserEmailIdentifier();
        roleManagerService.revokeRole(roleNameIdentifier, userEmailIdentifier);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRole(@RequestBody  RoleDto roleDto){
        Role role = RoleDtoToRole.fromRoleDto(roleDto);
        roleService.save(role);
    }

    @PostMapping("/{roleName}/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeRole(@PathVariable String roleName){
        roleService.remove(roleName);
    }
}
