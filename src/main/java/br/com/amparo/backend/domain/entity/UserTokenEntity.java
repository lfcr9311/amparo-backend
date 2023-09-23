package br.com.amparo.backend.domain.entity;

import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.domain.security.TokenUser;

import java.util.List;

public record UserTokenEntity(String id,
                              String email,
                              String name,
                              String password,
                              String salt,
                              List<String> roles) {

    public SaltedPassword getSaltedPassword() {
        return new SaltedPassword(salt, password);
    }

    public TokenUser toTokenUser() {
        return new TokenUser(id, email, name, roles);
    }
}
