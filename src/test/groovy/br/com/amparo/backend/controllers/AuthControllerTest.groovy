package br.com.amparo.backend.controllers

import br.com.amparo.backend.domain.entity.Doctor
import br.com.amparo.backend.domain.entity.User
import br.com.amparo.backend.dto.doctor.CreateDoctorRequest
import br.com.amparo.backend.dto.login.LoginRequest
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@Transactional
class AuthControllerTest extends Specification {
    @Autowired
    private MockMvc mockMvc
    @Shared
    private ObjectMapper mapper = new ObjectMapper()

    def "should login as patient"() {
        given: "User has email and password"
        String email = "teste@teste-paciente.com.br"
        String password = "paciente"

        when: "Login route is called"
        ResultActions result = mockMvc.perform(post("/auth/login")
                .content(mapper.writeValueAsString(new LoginRequest(email, password)))
                .contentType(MediaType.APPLICATION_JSON)
        )

        then: "Response must be 200 OK, and has token in it"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.token').isNotEmpty())
    }

    def "should return 401 when login and password does not exists"() {
        given: "User has email and password"
        String email = "teste@teste-paciente.com.br"
        String password = "pacient"

        when: "Login route is called"
        ResultActions result = mockMvc.perform(post("/auth/login")
                .content(mapper.writeValueAsString(new LoginRequest(email, password)))
                .contentType(MediaType.APPLICATION_JSON)
        )

        then: "Response must be 401, and has token in it"
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath('$.message').value("email of password invalid"))
    }

    @Ignore
    //@Fix
    def "Should create User"() {
        expect:
        MvcResult result = mockMvc.perform(post("/auth/register")
                .content(mapper.writeValueAsString(createUserPayload))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andReturn()

        and:
        when:
        User user = mapper.readValue(result.response.contentAsString, User)
        then:
        user == expectedUser

        where:
        createserPayload || expectedUser
        new CreateDoctorRequest() || Doctor.builder().build()
    }
}
