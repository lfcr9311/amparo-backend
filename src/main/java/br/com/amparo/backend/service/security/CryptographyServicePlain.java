package br.com.amparo.backend.service.security;

import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.service.CryptographyService;
public class CryptographyServicePlain implements CryptographyService {
    static final String SALT = "SALT";

    @Override
    public Boolean compare(String inputPassword, SaltedPassword saltedPassword) {
        return (inputPassword + saltedPassword.salt()).equals(saltedPassword.encryptedPassword());
    }

    @Override
    public SaltedPassword encrypt(String inputPassword) {
        return new SaltedPassword(SALT, inputPassword + SALT);
    }
}
