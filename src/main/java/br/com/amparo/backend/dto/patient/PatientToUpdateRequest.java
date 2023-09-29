package br.com.amparo.backend.dto.patient;

import br.com.amparo.backend.domain.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientToUpdateRequest(
        @NotNull
        @Schema(example = "Luna")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]{2,100}$", message = "Invalid name pattern")
        String name,
        @NotNull
        @Schema(example = "email@email.com.br")
        @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email pattern")
        String email,
        @NotBlank(message = "Cellphone is required")
        String cellphone,
        @NotNull
        @Pattern(regexp = "[0-9]{11}", message = "Cpf deve conter somente 11 números")
        String cpf,
        String profilePicture) {

    public Patient toPatient(String id) {
        return Patient.builder()
                .id(id)
                .email(this.email)
                .name(this.name)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .build();
    }
}
