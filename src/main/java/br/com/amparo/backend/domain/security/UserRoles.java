package br.com.amparo.backend.domain.security;

import java.util.stream.Stream;

public enum UserRoles {
    DOCTOR("ROLE_DOCTOR"),
    PATIENT("ROLE_PATIENT");

    private final String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static boolean hasRole(String role) {
        return Stream.of(values()).anyMatch(it -> ("ROLE_"+it.name()).equals(role));
    }
    public static UserRoles fromRole(String role) {
        return Stream.of(values())
                .filter(it->("ROLE_"+it.name()).equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not found role: " + role));
    }
}
