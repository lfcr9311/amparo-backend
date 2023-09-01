package br.com.amparo.backend.domain.record;

public record SaltedPassword(String salt, String encryptedPassword) {

}
