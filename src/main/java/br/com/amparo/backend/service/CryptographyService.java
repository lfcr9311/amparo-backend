package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.record.SaltedPassword;

public interface CryptographyService {
    Boolean compare(String inputPassword, SaltedPassword saltedPassword);

    SaltedPassword encrypt(String inputPassword);
}
