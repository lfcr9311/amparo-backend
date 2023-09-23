package br.com.amparo.backend.service.security;

import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.service.CryptographyService;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class CryptographyServiceSha256 implements CryptographyService {

    public static final int SALT_LENGTH = 16;
    private final MessageDigest sha256Digestor;
    private final Random random;

    public CryptographyServiceSha256(Random random) {
        try {
            this.sha256Digestor = MessageDigest.getInstance("SHA-256");
            this.random = random;
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

        //Required to control results on tests
        return RandomStringUtils.random(SALT_LENGTH, 0,0,saltHasLetters, saltHasNumbers, null, random);
    }
}
