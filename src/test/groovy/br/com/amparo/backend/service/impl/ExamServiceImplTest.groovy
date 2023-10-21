package br.com.amparo.backend.service.impl

import br.com.amparo.backend.dto.exam.CreateExamRequest
import br.com.amparo.backend.dto.exam.ExamResponse
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest
import br.com.amparo.backend.dto.patient.PatientResponse
import br.com.amparo.backend.repository.ExamRepository
import br.com.amparo.backend.repository.PatientRepository
import spock.lang.Specification

import org.assertj.core.api.Assertions;

import java.time.LocalDateTime

class ExamServiceImplTest extends Specification {

    ExamRepository examRepository

    PatientRepository patientRepository

    ExamServiceImpl examService

    def setup() {
        examRepository = Mock(ExamRepository.class)
        patientRepository = Mock(PatientRepository.class)
        examService = new ExamServiceImpl(examRepository, patientRepository)
    }

    def "addExam() should return empty when patient not found"() {
        given:
        def request = mockExamRequest()
        def id = "id"

        patientRepository.findById(_ as String) >> Optional.empty()

        when:
        def response = examService.addExam(request, id)

        then:
        Assertions.assertThat(response.isEmpty()).isTrue()
    }

    def "addExam() success should return exam when patient found"() {
        given:
        def request = mockExamRequest()
        def id = "id"

        patientRepository.findById(_ as String) >> Optional.of(mockPatientResponse())
        patientRepository.findById(_ as String).isEmpty() >> false

        examRepository.addExam(_ as CreateExamRequest, _ as String) >> Optional.of(mockExamResponse())

        when:
        def response = examService.addExam(new CreateExamRequest(
                "description",
                LocalDateTime.parse("2021-01-01T00:00:00"),
                true,
                "file",
                "image"
        ), id)

        then:
        Assertions.assertThat(response.get()).usingRecursiveComparison().isEqualTo(mockExamResponse())
    }

    def "listDoneExams must throw exception when patient not found"() {
        given:
        def id = "id"
        def page = 0
        def size = 10

        patientRepository.findById(_ as String) >> Optional.empty()

        when:
        examService.listDoneExams("id", 0, 10)

        then:
        thrown(RuntimeException.class)
    }

    def "listDoneExams should return a list of done exams"() {
        given:
        def id = "id"
        def page = 0
        def size = 10

        patientRepository.findById(_ as String) >> Optional.of(mockPatientResponse())
        patientRepository.findById(_ as String).isEmpty() >> false

        examRepository.listDoneExams("id", 0, 10) >> List.of(mockExamResponse())

        when:
        def response = examService.listDoneExams(id, page, size)

        then:
        Assertions.assertThat(response.get(0)).usingRecursiveComparison().isEqualTo(mockExamResponse())

        response.stream().forEach(exam -> exam.is_done())
    }

    def "listPendingExams must throw exception when patient not found"() {
        given:
        def id = "id"
        def page = 0
        def size = 10

        patientRepository.findById(_ as String) >> Optional.empty()

        when:
        examService.listPendingExams("id", 0, 10)


        then:
        thrown(RuntimeException.class)
    }

    def "listPendingExams should return a list of done exams"() {
        given:
        def id = "id"
        def page = 0
        def size = 10

        patientRepository.findById(_ as String) >> Optional.of(mockPatientResponse())
        patientRepository.findById(_ as String).isEmpty() >> false

        examRepository.listPendingExams("id", 0, 10) >> List.of(mockExamResponse())

        when:
        def response = examService.listPendingExams(id, page, size)

        then:
        Assertions.assertThat(response.get(0)).usingRecursiveComparison().isEqualTo(mockExamResponse())

        response.stream().forEach(exam -> !exam.is_done())
    }

    def "findExamById should return exam"() {
        given:
        def id = "id"

        examRepository.findExamById(_ as String) >> Optional.of(mockExamResponse())

        when:
        def response = examService.findExamById(id)

        then:
        Assertions.assertThat(response.get()).usingRecursiveComparison().isEqualTo(mockExamResponse())
    }

    def "editExam success"() {
        given:
        def id = "id"
        def request = mockExamToUpdateRequest()

        examRepository.editExam(_ as ExamToUpdateRequest, _ as String) >> Optional.of(mockExamResponse())

        when:
        def response = examService.editExam(request, id)

        then:
        Assertions.assertThat(response.get()).usingRecursiveComparison().isEqualTo(mockExamResponse())
    }

    def mockPatientResponse() {
        return new PatientResponse(
                "id",
                "user@email.com",
                "name",
                "cellphone",
                "profile_picture",
                true,
                "cpf"
        )
    }

    def mockExamRequest() {
        return new CreateExamRequest(
                "description",
                LocalDateTime.parse("2021-01-01T00:00:00"),
                true,
                "file",
                "image"
        )
    }

    def mockExamResponse() {
        return new ExamResponse(
                "id",
                "description",
                LocalDateTime.parse("2021-01-01T00:00:00"),
                true,
                "idPatient",
                "file",
                "image"
        )
    }

    def mockExamToUpdateRequest() {
        return new ExamToUpdateRequest(
                "description",
                LocalDateTime.parse("2021-01-01T00:00:00"),
                true,
                "file",
                "image"
        )
    }
}
