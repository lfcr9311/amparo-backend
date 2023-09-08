package br.com.amparo.backend.domain.record;

import java.nio.charset.StandardCharsets;

public record SaltedPassword(String salt, String encryptedPassword) {

    public byte[] getBytesPassword() {
        return this.encryptedPassword.getBytes(StandardCharsets.UTF_8);
    }

}
