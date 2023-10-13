package br.com.amparo.backend.service.impl

import br.com.amparo.backend.repository.LinkRepository
import spock.lang.Specification
import spock.lang.Unroll

class LinkServiceImplTest extends Specification {

    LinkRepository linkRepository

    LinkServiceImpl linkService

    def setup() {
        linkRepository = Mock(LinkRepository.class)
        linkService = new LinkServiceImpl(linkRepository)
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
