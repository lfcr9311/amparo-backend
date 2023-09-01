package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.service.CryptographyService;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CryptographyServiceImpl implements CryptographyService {

    public static final int SALT_LENGTH = 16;
    private final MessageDigest sha256Digestor;

    public CryptographyServiceImpl() {
        try {
            this.sha256Digestor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean compare(String inputPassword, String encryptedPassword, String salt) {
        var inputPass = inputPassword + salt;
        var encodedInputPassword = sha256Digestor.digest(inputPass.getBytes(StandardCharsets.UTF_8));

        return Arrays.equals(encodedInputPassword, encryptedPassword.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public SaltedPassword encrypt(String plainText) {
        var salt = generateSalt();
        var inputPass = plainText + salt;
        var encodedInputPassword = sha256Digestor.digest(inputPass.getBytes(StandardCharsets.UTF_8));
        var encodedPassword = new String(encodedInputPassword, StandardCharsets.UTF_8);

        return new SaltedPassword(salt, encodedPassword);
    }

    private String generateSalt() {
        boolean saltHasLetters = true;
        boolean saltHasNumbers = false;

        return RandomStringUtils.random(SALT_LENGTH, saltHasLetters, saltHasNumbers);
    }
}
