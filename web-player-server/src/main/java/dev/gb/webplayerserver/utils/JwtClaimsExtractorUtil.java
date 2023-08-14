package dev.gb.webplayerserver.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtClaimsExtractorUtil {
    public static final String USERNAME_CLAIM = "sub";

    private JwtClaimsExtractorUtil(){

    }

    public static String getUsername(Authentication authentication){
        if (!(authentication.getPrincipal() instanceof Jwt jwt))
            throw new UsernameNotFoundException("Username could not be found.");

        if(jwt.hasClaim(USERNAME_CLAIM)){
            return jwt.getClaim(USERNAME_CLAIM).toString();
        }

        throw new UsernameNotFoundException("Username could not be found.");
    }
}
