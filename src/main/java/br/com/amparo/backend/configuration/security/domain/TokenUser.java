package br.com.amparo.backend.configuration.security.domain;

import java.util.List;
public record TokenUser(String subject, String email, String name, List<String> roles) {
}
