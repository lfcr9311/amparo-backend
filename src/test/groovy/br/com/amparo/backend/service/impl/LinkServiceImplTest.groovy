package br.com.amparo.backend.service.impl

import br.com.amparo.backend.repository.LinkRepository
import br.com.amparo.backend.service.DoctorService
import br.com.amparo.backend.service.PatientService
import spock.lang.Specification
import spock.lang.Unroll

class LinkServiceImplTest extends Specification {

    LinkRepository linkRepository
    DoctorService doctorService
    LinkServiceImpl linkService
    PatientService patientService


    def setup() {
        linkRepository = Mock(LinkRepository.class)
        doctorService = Mock(DoctorService.class)
        patientService = Mock(PatientService.class)
        linkService = new LinkServiceImpl(linkRepository, doctorService, patientService)
    }

    def "should link doctor to patient"() {
        given:
        def doctorId = "doctorId"
        def patientId = "patientId"

        linkRepository.linkDoctorToPatient(_ as String, _ as String) >> true

        when:
        def result = linkService.linkDoctorToPatient(doctorId, patientId)

        then:
        result == true
    }

    def "should check connection between doctor and patient"() {
        given:
        def doctorId = "doctorId"
        def patientId = "patientId"

        linkRepository.checkConnection(_ as String, _ as String) >> true

        when:
        def result = linkService.checkConnection(doctorId, patientId)

        then:
        result == true
    }
}
