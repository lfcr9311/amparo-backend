package br.com.amparo.backend.service.security

import br.com.amparo.backend.domain.security.TokenUser
import com.auth0.jwt.JWT
import spock.lang.Specification

class TokenServiceTest extends Specification {
    private TokenService service = new TokenService("secret")

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
        def token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhbXBhcm8tYXBpIiwic3ViIjoidXNlcm5hbWUiLCJyb2xlcyI6WyJST0xFIl0sImVtYWlsIjoidXNlckBlbWFpbC5jb20iLCJuYW1lIjoicHJvZmlsZVBpY3R1cmUiLCJleHAiOjMxNTczODkxMjQ5MjJ9.1Yf7ekR8oYocRRftRLKBgJhbQap8jMoJgxuUCBQJN84"

        when:
        def res = service.validateToken(token)

        def authorities = new ArrayList<>()
        authorities.add("ROLE")

        then:
        res.isPresent()
        res.get().username == "user@email.com"
        res.get().accountNonExpired
        res.get().credentialsNonExpired
        res.get().accountNonLocked
        res.get().authorities.find { it.authority == "ROLE" }
    }

    def "should throw RunTimeException if token is null"() {
        given:
        def token = null

        when:
        def resp = service.validateToken(token)

        then:
        resp.isEmpty()
    }


}
