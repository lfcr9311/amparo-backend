package br.com.amparo.backend.domain.entity;

import br.com.amparo.backend.configuration.security.domain.ApiUser;
import br.com.amparo.backend.configuration.security.domain.TokenUser;
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
                              boolean isDoctor,
                              boolean isPatient) {

    public byte[] getBytePassword() {
        return this.password.getBytes(StandardCharsets.UTF_8);
    }

    public TokenUser toTokenUser() {
        List<String> roles = new ArrayList<>();
        if (isDoctor) roles.add("ROLE_DOCTOR");
        if (isPatient) roles.add("ROLE_PATIENT");
        return new TokenUser(id, email, roles);
    }
}
