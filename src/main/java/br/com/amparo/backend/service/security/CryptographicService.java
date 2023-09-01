package br.com.amparo.backend.service.security;

import java.security.MessageDigest;

public class CryptographicService {

    private MessageDigest messageDigest;

    public CryptographicService(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public boolean isEquals(String inputPassword, String salt, String userPassword) {
        /*
        var inputPassword = loginRequest.password() + userTokenEntity.salt();
                    var encodedInputPassword = sha256Digestor.digest(inputPassword.getBytes(StandardCharsets.UTF_8));
                    :point UP, exemplo.
         */
        return false;
    }

    //@TOdo Colocar logica para gerar SALT e criptgorafar senha.
}
