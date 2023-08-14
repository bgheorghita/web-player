package dev.gb.webplayerserver.mappers;

public interface DomainMapper<DTO, DOMAIN> {
    DOMAIN toDomain(DTO dto);
}
