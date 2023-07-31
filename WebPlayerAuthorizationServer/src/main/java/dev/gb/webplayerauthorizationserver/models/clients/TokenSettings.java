package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "token_settings")
@Getter
@Setter
public class TokenSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "authorization_code_ttl_minutes")
    private int authorizationCodeTTLMinutes;

    @Column(name = "access_token_ttl_minutes")
    private int accessTokenTTLMinutes;

    @Column(name = "access_token_format")
    private String accessTokenFormat;

    @Column(name = "reuse_refresh_token")
    private boolean reuseRefreshToken;

    @Column(name = "refresh_token_ttl_minutes")
    private int refreshTokenTTLMinutes;
}
