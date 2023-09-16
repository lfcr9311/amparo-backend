package br.com.amparo.backend.dto;

import br.com.amparo.backend.configuration.security.domain.UserRoles;

public record RegisterDto(String login, String password, UserRoles role) {
}
