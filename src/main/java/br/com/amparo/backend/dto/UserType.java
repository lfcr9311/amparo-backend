package br.com.amparo.backend.dto;

import java.util.stream.Stream;

public enum UserType {
    DOCTOR("ROLE_DOCTOR"),
    PATIENT("ROLE_PATIENT");

    private final String roleName;

    UserType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static boolean hasRole(String role) {
        return Stream.of(values()).anyMatch(it -> ("ROLE_"+it.name()).equals(role));
    }
    public static UserType fromRole(String role) {
        return Stream.of(values())
                .filter(it->("ROLE_"+it.name()).equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not found role: " + role));
    }
}
