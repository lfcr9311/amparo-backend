package br.com.amparo.backend.domain.security;

import java.util.List;
public record TokenUser(String subject, String email, String name, List<String> roles) {
}
