package br.com.amparo.backend.controllers

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
@Ignore
//@Fixme
class TestRouteControllerTest extends Specification {
    
    TestRouteController controller

    def setup() {
        controller = new TestRouteController();
    }

    def "GET test/patient must return message: Hello patient"() {
        given:
        def expectedResponse = Map.of("message", "Hello patient");

        when:
        def response = controller.patient()

        then:
        response.body == expectedResponse;

    }

    def "GET test/doctor must return message: Hello doctor"() {
        given:
        def expectedResponse = Map.of("message", "Hello doctor");

        when:
        def response = controller.doctor();

        then:
        response.body == expectedResponse;

    }


}
