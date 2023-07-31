package dev.gb.webplayerauthorizationserver.mappers.dtos.impl;

import dev.gb.webplayerauthorizationserver.dtos.AuthorityDto;
import dev.gb.webplayerauthorizationserver.mappers.dtos.DtoToDomainMapper;
import dev.gb.webplayerauthorizationserver.models.users.Authority;

public class AuthorityDtoToAuthority implements DtoToDomainMapper<AuthorityDto, Authority> {
    private final static AuthorityDtoToAuthority instance = new AuthorityDtoToAuthority();

    private AuthorityDtoToAuthority(){}

    @Override
    public Authority fromDto(AuthorityDto authorityDto) {
        Authority authority = new Authority();
        authority.setName(authorityDto.getName());
        return authority;
    }

    public static Authority fromAuthorityDto(AuthorityDto authorityDto){
        return instance.fromDto(authorityDto);
    }
}
