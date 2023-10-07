package br.com.amparo.backend.service.impl

import br.com.amparo.backend.domain.entity.Doctor
import br.com.amparo.backend.dto.doctor.DoctorResponse
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest
import br.com.amparo.backend.repository.DoctorRepository
import br.com.amparo.backend.service.UserService
import spock.lang.Specification

import org.assertj.core.api.Assertions;

class DoctorServiceImplTest extends Specification {

    UserService userService

    DoctorRepository doctorRepository

    DoctorServiceImpl service

    def setup() {
        userService = Mock(UserService.class)
        doctorRepository = Mock(DoctorRepository.class)
        service = new DoctorServiceImpl(userService, doctorRepository)
    }

    def "findDoctorById() must return doctor when doctor exists"() {
        given:
        def id = "id"
        def expectedResponse = mockDoctorResponse()

        doctorRepository.findDoctorById(_ as String) >> Optional.of(expectedResponse)

        when:
        def response = service.findDoctorById(id).get()

        then:
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse)
    }

    def "editDoctor must return empty when doctor doesnt exist"() {
        given:
        def request = mockDoctorToUpdateRequest()
        def id = "id"

        doctorRepository.findDoctorById(_ as String) >> Optional.empty()

        when:
        def response = service.editDoctor(request, id)

        then:
        Assertions.assertThat(response.isEmpty()).isTrue()
    }

    def "editDoctor with existing doctor"() {
        given:
        def id = "id"
        def request = mockDoctorToUpdateRequest()

        doctorRepository.findDoctorById(id) >> Optional.of(mockDoctorResponse())
        doctorRepository.updateDoctor(_ as Doctor) >> Optional.of(mockDoctorResponse())
        when:
        def response = service.editDoctor(request, id).get()

        then:
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(mockDoctorResponse())
    }

    def mockDoctorToUpdateRequest() {
        return new DoctorToUpdateRequest(
                "name",
                "12345678912",
                "RS/123456",
                "RS",
                "profile_picture"
        )
    }

    def mockDoctorResponse() {
        return new DoctorResponse(
                "id",
                "user@email.com",
                "john doe",
                "12345678912",
                "profilePicture_value",
                true,
                "RS/123456",
                "RS"
        )
    }
}
