package dev.gb.webplayerauthorizationserver.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto {
    private String name;
    private Set<AuthorityDto> authoritySet;
}
