package br.com.amparo.backend.controllers

import br.com.amparo.backend.DTO.LoginRequest
import br.com.amparo.backend.service.security.AuthService
import org.json.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType
import spock.lang.Shared
import spock.lang.Specification

@Sql
class AuthenticationControllerTest extends Specification {
    @Shared
    AuthService authService

    @Shared
    AuthenticationController authenticationController

    @Autowired
    MockMvc mockMvc

    def setup() {
        authService = Mock(AuthService.class)
        authenticationController = new AuthenticationController(authService)
    }

    def "should return 200 when login with valid credentials"() {
        given:
        def loginRequest = new LoginRequest("user@email.com", "123")

        when:
        mockMvc.perform

        then:

    }
}
