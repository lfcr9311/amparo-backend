package br.com.amparo.backend.service.security

import br.com.amparo.backend.configuration.security.domain.TokenUser
import com.auth0.jwt.JWT
import spock.lang.Shared
import spock.lang.Specification

class TokenServiceTest extends Specification {
    @Shared
    TokenService service

    def setup() {
        service = new TokenService("secret")
    }

    def "should generate a token with valid user data"() {
        given:
        def user = new TokenUser("username", "user@email.com", "profilePicture", List<String>.of("ROLE"))

        when:
        def token = service.generateToken(user)
        def decodedToken = JWT.decode(token)

        then:
        decodedToken.subject == user.subject()
        decodedToken.getClaim("roles").asList(String) == user.roles()
        decodedToken.getClaim("email").asString() == user.email()
    }

    def "should throw RunTimeException when user is null"() {
        given:
        def user = null

        when:
        service.generateToken(user)

        then:
        thrown(RuntimeException)
    }

    def "should validate token"() {
        given:
        def token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhbXBhcm8tYXBpIiwic3ViIjoidXNlcm5hbWUiLCJyb2xlcyI6WyJST0xFIl0sImVtYWlsIjoidXNlckBlbWFpbC5jb20iLCJleHAiOjE2OTM4NzYyMTl9.DktHj4j-DwmF5WazCCw74PCswR4_piuY-H2PvetLrmw"

        when:
        def res = service.validateToken(token)

        def authorities = new ArrayList<>()
        authorities.add("ROLE")

        then:
        res.username == "user@email.com"
        res.accountNonExpired
        res.credentialsNonExpired
        res.accountNonLocked
        res.authorities.find { it.authority == "ROLE" }
    }

    def "should throw RunTimeException if token is null"() {
        given:
        def token = null

        when:
        service.validateToken(token)

        then:
        thrown(RuntimeException)
    }


}
