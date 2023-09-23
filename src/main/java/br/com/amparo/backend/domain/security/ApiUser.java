package br.com.amparo.backend.domain.security;

import br.com.amparo.backend.dto.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class ApiUser extends User {
    @Getter
    private String id;
    @Getter
    private String name;
    private List<UserType> roles;

    public ApiUser(String id, String email, String name, String token,
                   Collection<? extends GrantedAuthority> authorities) {
        super(email, token, authorities);
        this.roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(UserType::hasRole)
                .map(UserType::fromRole)
                .toList();
        this.id = id;
        this.name = name;
    }

    public boolean hasRole(UserType role) {
        return roles.contains(role);
    }

    public boolean isDoctor() {
        return hasRole(UserType.DOCTOR);
    }

    public boolean isPatient() {
        return hasRole(UserType.PATIENT);
    }
}
