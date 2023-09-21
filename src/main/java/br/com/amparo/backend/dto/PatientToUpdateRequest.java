package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Patient;

import java.util.UUID;

public record PatientToUpdateRequest(String id, String name, String email, String password, String profilePicture, String cellphone, String cpf) {
    public Patient toPatient() {
        return Patient.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .build();
    }

    public Patient toPatient(String id) {
        return Patient.builder()
                .id(id)
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .build();
    }
}
