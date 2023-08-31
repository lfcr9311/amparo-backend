package br.com.amparo.backend.domain.entity;

import java.nio.charset.StandardCharsets;

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
}
