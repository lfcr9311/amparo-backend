package br.com.amparo.backend.dto.doctor;

import br.com.amparo.backend.domain.entity.Doctor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorToUpdateRequest(
        @NotNull
        @Schema(example = "email@email.com.br")
        @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email pattern")
        String email,
        @NotNull
        @Schema(example = "Apollo")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]{2,100}$", message = "Invalid name pattern")
        String name,
        @NotBlank(message = "Cellphone is required")
        String cellphone,
        @NotNull
        @Pattern(regexp = "\\d{4,6}", message = "CRM should contain between 4 to 6 digits")
        String crm,
        @NotNull
        @Pattern(regexp = "[A-Z]{2}", message = "UF must contain only 2 characters")
        String uf,
        String profilePicture) {

    public Doctor toDoctor(String id) {
        return Doctor.builder()
                .id(id)
                .email(this.email)
                .name(this.name)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .crm(this.crm)
                .uf(this.uf)
                .build();
    }
}
