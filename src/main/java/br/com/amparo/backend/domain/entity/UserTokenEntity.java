package br.com.amparo.backend.domain.entity;

import br.com.amparo.backend.configuration.security.domain.ApiUser;
import br.com.amparo.backend.configuration.security.domain.TokenUser;
import br.com.amparo.backend.domain.record.SaltedPassword;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public record UserTokenEntity(String id,
                              String email,
                              String name,
                              String password,
                              String salt,
                              String profilePicture,
                              String cellphone,
                              List<String> roles) {

    public SaltedPassword getSaltedPassword() {
        return new SaltedPassword(salt, password);
    }

    public TokenUser toTokenUser() {
        return new TokenUser(id, email, profilePicture, roles);
    }
}
