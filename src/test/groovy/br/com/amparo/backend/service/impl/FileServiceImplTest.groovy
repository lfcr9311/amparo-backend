package br.com.amparo.backend.service.impl

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class FileServiceImplTest extends Specification {
    @Shared
    FileServiceImpl fileService

    def setup() {
        fileService = new FileServiceImpl()
    }

    def "convertFileToBase64() with valid file"() {
        given:
        File file = File.createTempFile("testFile", ".txt")
        file.write("Hello, Spock!")

        when:
        String result = fileService.convertFileToBase64(file)

        then:
        result == "SGVsbG8sIFNwb2NrIQ=="

        cleanup:
        file.delete()
    }

    def "convertFileToBase64 with invalid file"() {
        given:
        File file = new File("invalid/path/to/file.txt")

        when:
        String result = fileService.convertFileToBase64(file)

        then:
        result == null
    }

    @Unroll
    def "test decodeBase64 with valid input '#base64'"() {
        expect:
        fileService.decodeBase64(base64) == expected

        where:
        base64       || expected
        "SGVsbG8="   || "Hello"
        "U3BvY2s="   || "Spock"
    }

    def "test decodeBase64 with invalid input"() {
        given:
        String base64 = "invalid_base64"

        when:
        String result = fileService.decodeBase64(base64)

        then:
        result == null
    }
}
