package dev.gb.webplayerauthorizationserver.mappers;

import dev.gb.webplayerauthorizationserver.models.clients.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Set;
import java.util.function.Consumer;

public class ClientMapper {
    public static RegisteredClient from(Client client){
        var tokenSettings = client.getTokenSettings();
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(getClientAuthenticationMethods(client.getAuthenticationMethodSet()))
                .authorizationGrantTypes(getAuthorizationGrantTypes(client.getGrantTypeSet()))
                .redirectUris(getRedirectUris(client.getRedirectUriSet()))
                .scopes(getScopes(client.getScopeSet()))
                .tokenSettings(TokenSettings.builder()
                        .authorizationCodeTimeToLive(Duration.ofMinutes(tokenSettings.getAuthorizationCodeTTLMinutes()))
                        .accessTokenTimeToLive(Duration.ofMinutes(tokenSettings.getAccessTokenTTLMinutes()))
                        .accessTokenFormat(new OAuth2TokenFormat(tokenSettings.getAccessTokenFormat()))
                        .reuseRefreshTokens(tokenSettings.isReuseRefreshToken())
                        .refreshTokenTimeToLive(Duration.ofMinutes(tokenSettings.getRefreshTokenTTLMinutes()))
                        .build())
                .build();
    }

    private static Consumer<Set<String>> getScopes(Set<Scope> scopeSet) {
        return scopes -> {
            for (Scope scope : scopeSet){
                scopes.add(scope.getScope());
            }
        };
    }


    private static Consumer<Set<String>> getRedirectUris(Set<RedirectUri> redirectUriSet) {
        return redirectUri -> {
            for (RedirectUri redirectUrl : redirectUriSet){
                redirectUri.add(redirectUrl.getUri());
            }
        };
    }

    private static Consumer<Set<ClientAuthenticationMethod>> getClientAuthenticationMethods(Set<AuthenticationMethod> authenticationMethodSet) {
        return clientAuthenticationMethods -> {
            for (AuthenticationMethod authenticationMethod : authenticationMethodSet){
                ClientAuthenticationMethod clientAuthenticationMethod = new ClientAuthenticationMethod(authenticationMethod.getAuthenticationMethod());
                clientAuthenticationMethods.add(clientAuthenticationMethod);
            }
        };
    }

    private static Consumer<Set<AuthorizationGrantType>> getAuthorizationGrantTypes(Set<GrantType> grantTypeSet){
        return authorizationGrantTypes -> {
            for(GrantType grantType : grantTypeSet){
                AuthorizationGrantType authorizationGrantType = new AuthorizationGrantType(grantType.getGrantType());
                authorizationGrantTypes.add(authorizationGrantType);
            }
        };
    }
}
