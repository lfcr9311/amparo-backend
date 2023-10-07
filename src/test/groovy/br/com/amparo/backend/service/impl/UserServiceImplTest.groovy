package br.com.amparo.backend.service.impl

import br.com.amparo.backend.domain.entity.User
import br.com.amparo.backend.repository.UserRepository
import org.assertj.core.api.Assertions
import spock.lang.Specification

class UserServiceImplTest extends Specification {
    UserRepository repository

    UserServiceImpl service

    def setup() {
        repository = Mock(UserRepository.class)
        service = new UserServiceImpl(repository)
    }

    def "update user"() {
        given:
        def user = mockUser()

        repository.updateUser(_ as User) >> Optional.of(user)

        when:
        def response = service.updateUser(user)

        then:
        Assertions.assertThat(response.get()).usingRecursiveComparison().isEqualTo(user)

    }

    def mockUser() {
        return new User(
                "id",
                "user@email.com",
                "psw",
                "salt",
                "name",
                "cellphone",
                "profile_picture",
                true
        )
    }
}
