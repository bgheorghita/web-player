package dev.gb.webplayerauthorizationserver.mappers;

import dev.gb.webplayerauthorizationserver.models.clients.Scope;

public class ScopeMapper {
    public static Scope from(String scopeName){
        Scope scope = new Scope();
        scope.setScope(scopeName);
        return scope;
    }
}
