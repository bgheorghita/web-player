package dev.gb.webplayerauthorizationserver.mappers.dtos.impl;

import dev.gb.webplayerauthorizationserver.dtos.RoleDto;
import dev.gb.webplayerauthorizationserver.mappers.dtos.DtoToDomainMapper;
import dev.gb.webplayerauthorizationserver.models.users.Authority;
import dev.gb.webplayerauthorizationserver.models.users.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleDtoToRole implements DtoToDomainMapper<RoleDto, Role> {
    private static final RoleDtoToRole instance = new RoleDtoToRole();
    private RoleDtoToRole(){}

    @Override
    public Role fromDto(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setAuthoritySet(getAuthorities(roleDto));
        return role;
    }

    private static Set<Authority> getAuthorities(RoleDto roleDto) {
        return roleDto.getAuthoritySet()
                .stream()
                .map(AuthorityDtoToAuthority::fromAuthorityDto)
                .collect(Collectors.toSet());
    }

    public static Role fromRoleDto(RoleDto roleDto){
        return instance.fromDto(roleDto);
    }
}
