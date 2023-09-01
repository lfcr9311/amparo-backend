package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.record.SaltedPassword;

public interface CryptographyService {
    Boolean compare(String inputPassword, String encryptedPassword, String salt);

    SaltedPassword encrypt(String inputPassword);
}
