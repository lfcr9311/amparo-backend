package br.com.amparo.backend.service.impl


import br.com.amparo.backend.domain.entity.Patient
import br.com.amparo.backend.dto.patient.PatientResponse
import br.com.amparo.backend.dto.patient.PatientToUpdateRequest
import br.com.amparo.backend.repository.PatientRepository
import br.com.amparo.backend.service.UserService
import org.assertj.core.api.Assertions
import spock.lang.Specification

class PatientServiceImplTest extends Specification {

    UserService userService

    PatientRepository patientRepository

    PatientServiceImpl service

    def setup() {
        userService = Mock(UserService.class)
        patientRepository = Mock(PatientRepository.class)
        service = new PatientServiceImpl(userService, patientRepository)
    }

    def "should find patient by cpf"() {
        given:
        def cpf = "cpf"

        patientRepository.findByCpf(_ as String) >> Optional.of(mockPatientResponse())

        when:
        def response = service.findPatientByCpf(cpf).get()

        then:
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(mockPatientResponse())
    }

    def "should find patient by id"() {
        given:
        def id = "id"

        patientRepository.findById(_ as String) >> Optional.of(mockPatientResponse())

        when:
        def response = service.findPatientById(id).get()

        then:
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(mockPatientResponse())
    }

    def "edit patient should return empty if patient dont exist"() {
        given:
        def id = "id"
        def request = mockPatientToUpdateRequest()

        patientRepository.findById(_ as String) >> Optional.empty()

        when:
        def response = service.editPatient(request, id)

        then:
        Assertions.assertThat(response).isEmpty()

    }

    def "edit patient should update user"() {
        given:
        def id = "id"
        def request = mockPatientToUpdateRequest()

        patientRepository.findById(id) >> Optional.of(mockPatientResponse())
        patientRepository.updatePatient(_ as Patient) >> Optional.of(mockPatientResponse())
        when:
        def response = service.editPatient(request, id).get()

        then:
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(mockPatientResponse())
    }

    def mockPatientToUpdateRequest() {
        return new PatientToUpdateRequest(
                "name",
                "patient@email.com",
                "cellphone",
                "cpf",
                "today",
                "12354",
                "profile_picture",

        )
    }

    def mockPatientResponse() {
        return new PatientResponse(
                "id",
                "patient@email.com",
                "john doe",
                "cellphone",
                "profile_picture",
                true,
                "cpf",
                "today",
                "123465"
        )
    }
}
