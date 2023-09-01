package br.com.amparo.backend.configuration.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
public class TokenUser extends User {
    public TokenUser(String username, String token, Collection<? extends GrantedAuthority> authorities) {
        super(username, token, authorities);
    }
}
