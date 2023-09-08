package br.com.amparo.backend.service.security

import br.com.amparo.backend.domain.record.SaltedPassword
import br.com.amparo.backend.service.CryptographyService
import br.com.amparo.backend.service.impl.CryptographyServiceSha256
import spock.lang.Specification

class CryptographyServiceSha256Test extends Specification {

    private static final long RANDOM_SEED = 2221564899842315
    private static final SaltedPassword expectedPass = new SaltedPassword("pMjysHWuCIOBhDRW",
            "qilxkhg7SoTO0rP/ve42H2l87LMn0rqo+BfweOoJ2zg=")
    CryptographyService cryptographyService = new CryptographyServiceSha256(new Random(RANDOM_SEED))

    def 'should create new SlatedPassword'() {
        expect:
        expectedPass == cryptographyService.encrypt('batata')
    }

    def 'should compare password'() {
        expect:
        cryptographyService.compare('batata', expectedPass)
    }
}
