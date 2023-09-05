package br.com.amparo.backend.configuration.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class ApiUser extends User {
    private String id;
    private String name;
    public ApiUser(String id, String email, String name, String token,
                   Collection<? extends GrantedAuthority> authorities) {
        super(email, token, authorities);
        this.id = id;
        this.name = name;
    }
}
