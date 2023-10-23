package br.com.amparo.backend.service.security

import br.com.amparo.backend.domain.entity.Doctor
import br.com.amparo.backend.domain.entity.Patient
import br.com.amparo.backend.domain.entity.User
import br.com.amparo.backend.domain.entity.UserTokenEntity
import br.com.amparo.backend.domain.record.SaltedPassword
import br.com.amparo.backend.domain.security.TokenUser
import br.com.amparo.backend.dto.CreateUserRequest
import br.com.amparo.backend.dto.UserType
import br.com.amparo.backend.dto.doctor.CreateDoctorRequest
import br.com.amparo.backend.dto.login.LoginRequest
import br.com.amparo.backend.dto.patient.CreatePatientRequest
import br.com.amparo.backend.exception.UserAlreadyExistsException
import br.com.amparo.backend.repository.DoctorRepository
import br.com.amparo.backend.repository.PatientRepository
import br.com.amparo.backend.repository.UserRepository
import br.com.amparo.backend.repository.UserTokenRepository
import br.com.amparo.backend.service.CryptographyService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class AuthServiceTest extends Specification {

    @Shared
    DoctorRepository doctorRepository

    @Shared
    TokenService tokenService

    @Shared
    UserTokenRepository userTokenRepository

    @Shared
    CryptographyService cryptographyService

    @Shared
    UserRepository userRepository

    @Shared
    PatientRepository patientRepository

    @Shared
    AuthService authService

    def setup() {
        doctorRepository = Mock(DoctorRepository)
        tokenService = Mock(TokenService)
        userTokenRepository = Mock(UserTokenRepository)
        cryptographyService = Mock(CryptographyService)
        userRepository = Mock(UserRepository)
        patientRepository = Mock(PatientRepository)
        authService = new AuthService(tokenService, userTokenRepository, cryptographyService, userRepository, patientRepository, doctorRepository)
    }

    def "login should return token when request is valid"() {
        given:
        def loginRequest = new LoginRequest("user@email.com", "password")
        def userTokenEntity = new UserTokenEntity("id", "user@email.com", "user", "password", "salt", [])

        userTokenRepository.findUserByEmail(_ as String) >> Optional.of(userTokenEntity)
        cryptographyService.compare(_ as String, _ as SaltedPassword) >> true
        tokenService.generateToken(_ as TokenUser) >> "validToken"

        when:
        def res = authService.login(loginRequest)

        then:
        res.isPresent()
        res.get() == "validToken"
    }

    def "login should return empty when request is invalid"() {
        given:
        def loginRequest = new LoginRequest("user@email.com", "password")

        userTokenRepository.findUserByEmail(_ as String) >> Optional.empty()

        when:
        def res = authService.login(loginRequest)

        then:
        res.isEmpty()
    }

    def "register must register patient when request is patient"() {
        given:
        CreatePatientRequest request = new CreatePatientRequest()
        request.setEmail("user@email.com")

        SaltedPassword saltedPassword = new SaltedPassword("password", "salt")
        userRepository.findByEmail(_ as String) >> Optional.empty()
        cryptographyService.encrypt(_ as String) >> saltedPassword
        userRepository.create(_ as User, _ as SaltedPassword) >> "id"
        patientRepository.create(_ as Patient) >> true

        when:
        User result = authService.register(request)

        then:
        result instanceof Patient
    }

    def "register must register doctor when request is doctor"() {
        given:
        CreateDoctorRequest request = new CreateDoctorRequest()
        request.setEmail("email")

        SaltedPassword saltedPassword = new SaltedPassword("password", "salt")
        userRepository.findByEmail(_ as String) >> Optional.empty()
        cryptographyService.encrypt(_ as String) >> saltedPassword
        userRepository.create(_ as User, _ as SaltedPassword) >> "id"
        doctorRepository.create(_ as Doctor) >> true

        when:
        User result = authService.register(request)

        then:
        result instanceof Doctor
    }

    def "register must throw UserAlreadyExistsException when user already exists"() {
        given:
        CreateDoctorRequest request = new CreateDoctorRequest()
        request.setEmail("email")

        SaltedPassword saltedPassword = new SaltedPassword("password", "salt")
        User user = new User("id", "email", "password", "salt", "name", "cellphone", "profilePicture", true)
        userRepository.findByEmail(_ as String) >> Optional.of(user)

        when:
        authService.register(request)

        then:
        thrown(UserAlreadyExistsException)
    }
}
