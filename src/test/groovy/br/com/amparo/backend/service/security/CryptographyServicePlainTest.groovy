package br.com.amparo.backend.service.security

import br.com.amparo.backend.domain.record.SaltedPassword
import spock.lang.Shared
import spock.lang.Specification

class CryptographyServicePlainTest extends Specification {

    @Shared
    CryptographyServicePlain cryptographyService;

    def setup() {
        cryptographyService = new CryptographyServicePlain()
    }

    def "compare should return true when the passwords are equal"() {
        given: "a salted password"
        def input = "password"
        def saltedPassword = new SaltedPassword(CryptographyServicePlain.SALT, "password" + CryptographyServicePlain.SALT)

        when: "compare is called"
        def result = cryptographyService.compare(input, saltedPassword)

        then:
        result == true
    }


    def "compare should return false when the passwords are different"() {
        given:
        def input = "password"
        def saltedPassword = new SaltedPassword(CryptographyServicePlain.SALT, "wrongpassword" + CryptographyServicePlain.SALT)

        when:
        def result = cryptographyService.compare(input, saltedPassword)

        then:
        result == false
    }

    def "encrypt() should return the salted password"() {
        given:
        def result = cryptographyService.encrypt("password")

        expect:
        result.encryptedPassword() == "password" + CryptographyServicePlain.SALT
    }
}
