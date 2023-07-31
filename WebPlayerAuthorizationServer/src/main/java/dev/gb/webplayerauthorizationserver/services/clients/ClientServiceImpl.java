package dev.gb.webplayerauthorizationserver.services.clients;

import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.mappers.ClientMapper;
import dev.gb.webplayerauthorizationserver.mappers.GrantTypeMapper;
import dev.gb.webplayerauthorizationserver.mappers.RedirectUriMapper;
import dev.gb.webplayerauthorizationserver.mappers.ScopeMapper;
import dev.gb.webplayerauthorizationserver.models.clients.*;
import dev.gb.webplayerauthorizationserver.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        Client client = new Client();
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());

        Set<AuthenticationMethod> authenticationMethodSet = getAuthenticationMethods(registeredClient);
        client.setAuthenticationMethodSet(authenticationMethodSet);

        Set<Scope> scopeSet = getScopes(registeredClient);
        client.setScopeSet(scopeSet);

        Set<GrantType> grantTypeSet = getGrantTypes(registeredClient);
        client.setGrantTypeSet(grantTypeSet);

        Set<RedirectUri> redirectUriSet = getRedirectUrls(registeredClient, client);
        client.setRedirectUriSet(redirectUriSet);

        clientRepository.save(client);
    }

    private Set<RedirectUri> getRedirectUrls(RegisteredClient registeredClient, Client client) {
        Set<RedirectUri> redirectUriSet = new HashSet<>();
        registeredClient
                .getRedirectUris()
                .forEach(url -> {
                    RedirectUri redirectUri = RedirectUriMapper.from(url, client);
                    redirectUriSet.add(redirectUri);
                });
        return redirectUriSet;
    }

    private Set<GrantType> getGrantTypes(RegisteredClient registeredClient) {
        Set<GrantType> grantTypeSet = new HashSet<>();
        registeredClient
                .getAuthorizationGrantTypes()
                .forEach(authorizationGrantType -> {
                    GrantType grantType = GrantTypeMapper.from(authorizationGrantType.getValue());
                    grantTypeSet.add(grantType);
                });
        return grantTypeSet;
    }

    private Set<Scope> getScopes(RegisteredClient registeredClient) {
        Set<Scope> scopeSet = new HashSet<>();
        registeredClient
                .getScopes()
                .forEach(scopeValue -> {
                    Scope scope = ScopeMapper.from(scopeValue);
                    scopeSet.add(scope);
                });
        return scopeSet;
    }

    private Set<AuthenticationMethod> getAuthenticationMethods(RegisteredClient registeredClient) {
        Set<String> authenticationMethodValues = registeredClient
                .getClientAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.toSet());

        Set<AuthenticationMethod> authenticationMethodSet = new HashSet<>();
        authenticationMethodValues.forEach(method -> {
            AuthenticationMethod authenticationMethod = new AuthenticationMethod();
            authenticationMethod.setAuthenticationMethod(method);
            authenticationMethodSet.add(authenticationMethod);
        });
        return authenticationMethodSet;
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<Client> client = clientRepository.findById(Integer.parseInt(id));
        if (client.isEmpty()){
            throw new ResourceNotFoundException("Client id " + id + " has not been found.");
        }

        return ClientMapper.from(client.get());
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Client> client = clientRepository.findByClientId(clientId);
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Client " + clientId + " has not been found.");
        }

        return ClientMapper.from(client.get());
    }
}
