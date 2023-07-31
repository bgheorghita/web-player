package dev.gb.webplayerauthorizationserver.mappers;

import dev.gb.webplayerauthorizationserver.models.clients.Client;
import dev.gb.webplayerauthorizationserver.models.clients.RedirectUri;

public class RedirectUriMapper {
    public static RedirectUri from(String uri, Client client){
        RedirectUri redirectUri = new RedirectUri();
        redirectUri.setUri(uri);
        redirectUri.setClient(client);
        return redirectUri;
    }
}
