package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Patient;
import java.util.UUID;

public record PatientResponse(UUID id, String email, String name, String cellphone, String profilePicture, boolean isAnonymous, String cpf) {
    public PatientResponse mapToCreatePatientResponse(Patient patient) {
        return new PatientResponse(
                UUID.fromString(patient.getId()),
                patient.getEmail(),
                patient.getName(),
                patient.getCellphone(),
                patient.getProfilePicture(),
                patient.isAnonymous(),
                patient.getCpf()
        );
    }
}
