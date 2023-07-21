package dev.gb.webplayerserver.mappers;

public interface DtoMapper<DOMAIN, DTO> {
    DOMAIN fromDto(DTO dto);
    DTO toDto(DOMAIN domain);
}
