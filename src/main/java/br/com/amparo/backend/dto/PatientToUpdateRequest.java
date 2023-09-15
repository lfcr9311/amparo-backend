package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Patient;
public record PatientToUpdateRequest(String name, String email, String password, String profilePicture, String cellphone, String cpf) {
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
}
