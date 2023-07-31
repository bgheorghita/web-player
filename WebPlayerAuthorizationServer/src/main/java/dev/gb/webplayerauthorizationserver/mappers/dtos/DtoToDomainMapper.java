package dev.gb.webplayerauthorizationserver.mappers.dtos;

public interface DtoToDomainMapper<DTO, DOMAIN> {
    DOMAIN fromDto(DTO dto);
}
