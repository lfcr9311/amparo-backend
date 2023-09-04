package br.com.amparo.backend.DTO;

import br.com.amparo.backend.configuration.security.domain.UserRoles;

public record RegisterDto(String login, String password, UserRoles role) {
}
