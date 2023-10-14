package br.com.amparo.backend.service.security

import br.com.amparo.backend.domain.security.ApiUser
import org.assertj.core.api.Assertions
import spock.lang.Shared
import spock.lang.Specification
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder


class SecurityUtilsTest extends Specification {
    @Shared
    Authentication authentication

    @Shared
    SecurityContext securityContext

    @Shared
    SecurityContextHolder securityContextHolder

    def setup() {
        authentication = Mock(Authentication)
        securityContext = Mock(SecurityContext)
        securityContextHolder = Mock(SecurityContextHolder)
    }

    def "getApiUser should return the ApiUser as principal"() {
        given:
        def apiUser = new ApiUser("1", "user@email.com", "User", "token", [])

        securityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        authentication.getPrincipal() >> apiUser

        when:
        def result = SecurityUtils.getApiUser()

        then:
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(apiUser)
    }

    def "getCurrentUserId should return the current ApiUser id"() {
        given:
        def userID = "1"
        def apiUser = new ApiUser(userID, "user@email.com", "User", "token", [])


        securityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        authentication.getPrincipal() >> apiUser

        when:
        def result = SecurityUtils.getCurrentUserId()

        then:
        Assertions.assertThat(result).isEqualTo(userID)
    }

}
