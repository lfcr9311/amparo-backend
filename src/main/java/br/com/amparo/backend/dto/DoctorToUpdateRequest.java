package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Doctor;

public record DoctorToUpdateRequest(String id, String name, String email, String password, String profilePicture, String cellphone, String crm, String uf) {
    public Doctor toDoctor() {
        return Doctor.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .crm(this.crm)
                .uf(this.uf)
                .build();
    }

    public Doctor toDoctor(String id) {
        return Doctor.builder()
                .id(id)
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .crm(this.crm)
                .uf(this.uf)
                .build();
    }

    public boolean isValid() {
        return this.name != null && this.email != null &&
                this.password != null && this.cellphone != null
                && this.crm != null && this.uf != null;
    }
}
