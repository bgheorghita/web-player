package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_authentication_method",
            joinColumns = @JoinColumn(name = "client_id"), // the entity id, not clientId
            inverseJoinColumns = @JoinColumn(name = "authentication_method_id")
    )
    private Set<AuthenticationMethod> authenticationMethodSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_grant_type",
            joinColumns = @JoinColumn(name = "client_id"), // the entity id, not clientId
            inverseJoinColumns = @JoinColumn(name = "grant_type_id")
    )
    private Set<GrantType> grantTypeSet = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<RedirectUri> redirectUriSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_scope",
            joinColumns = @JoinColumn(name = "client_id"), // the entity id, not clientId
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<Scope> scopeSet = new HashSet<>();

    @OneToOne
    private TokenSettings tokenSettings;
}
