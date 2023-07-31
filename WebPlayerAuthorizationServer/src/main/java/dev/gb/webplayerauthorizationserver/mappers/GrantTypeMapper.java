package dev.gb.webplayerauthorizationserver.mappers;

import dev.gb.webplayerauthorizationserver.models.clients.GrantType;

public class GrantTypeMapper {
    public static GrantType from(String grantTypeName){
        GrantType grantType = new GrantType();
        grantType.setGrantType(grantTypeName);
        return grantType;
    }
}
