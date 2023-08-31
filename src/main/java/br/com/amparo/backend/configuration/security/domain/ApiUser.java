package br.com.amparo.backend.configuration.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ApiUser extends User {
    private String id;
    public ApiUser(String id, String username, String token, Collection<? extends GrantedAuthority> authorities) {
        super(username, token, authorities);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
