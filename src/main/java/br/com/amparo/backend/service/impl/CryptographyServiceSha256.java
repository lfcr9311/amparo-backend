package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.service.CryptographyService;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class CryptographyServiceSha256 implements CryptographyService {

    public static final int SALT_LENGTH = 16;
    private final MessageDigest sha256Digestor;

    public CryptographyServiceSha256() {
        try {
            this.sha256Digestor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean compare(String inputPassword, SaltedPassword saltedPassword) {
        var inputPass = inputPassword + saltedPassword.salt();
        var hashedInputPassword = sha256Digestor.digest(inputPass.getBytes(StandardCharsets.UTF_8));
        var encodedInputPassword = Base64.getEncoder().encodeToString(hashedInputPassword);
        return encodedInputPassword.equals(saltedPassword.encryptedPassword());
    }

    @Override
    public SaltedPassword encrypt(String plainText) {
        var salt = generateSalt();
        var inputPass = plainText + salt;
        var encodedInputPassword = sha256Digestor.digest(inputPass.getBytes(StandardCharsets.UTF_8));
        var encodedPassword = Base64.getEncoder().encodeToString(encodedInputPassword);
        return new SaltedPassword(salt, encodedPassword);
    }

    private String generateSalt() {
        boolean saltHasLetters = true;
        boolean saltHasNumbers = false;

        return RandomStringUtils.random(SALT_LENGTH, saltHasLetters, saltHasNumbers);
    }
}
