package br.com.amparo.backend.service.impl

import br.com.amparo.backend.dto.medicine.MedicineResponse
import br.com.amparo.backend.repository.MedicineRepository
import org.assertj.core.api.Assertions
import spock.lang.Specification

class MedicineServiceImplTest extends Specification {

    MedicineRepository repository

    MedicineServiceImpl service

    def setup() {
        repository = Mock(MedicineRepository.class)
        service = new MedicineServiceImpl(repository)
    }

    def "should find medicine by id"() {
        given:
        def id = 1
        def medicine = mockMedicineResponse()
        repository.findMedicineById(id) >> Optional.of(medicine)

        when:
        def result = service.findMedicineById(id).get()

        then:
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(medicine)
    }

    def "should find medicine by name"() {
        given:
        def name = "name"
        def medicine = mockMedicineResponse()
        repository.findMedicineByName(name) >> Optional.of(medicine)

        when:
        def result = service.findMedicineByName(name).get()

        then:
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(medicine)
    }

    def "should find all medicines"() {
        given:
        def pageNumber = 0
        def pageSize = 10

        def medicine = mockMedicineResponse()
        repository.findAllMedicines(0, 10) >> List.of(medicine)

        when:
        def result = service.findAllMedicines(pageNumber, pageSize)

        then:
        Assertions.assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(medicine)
    }

    def mockMedicineResponse() {
        return new MedicineResponse(
                1,
                "name",
                "description",
        )
    }
}
