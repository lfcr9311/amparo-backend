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
        String cellphone,
        @NotNull
        @Pattern(regexp = "[0-9]{11}", message = "CPF must contain only 11 numbers")
        String cpf,
        @NotNull
        @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$", message = "Date should be DD/MM/YYYY")
        String birthDate,
        @Pattern(regexp = "\\d{15}$", message = "SUS number must contain 15 digits")
        String numSus,
        String profilePicture) {

    public Patient toPatient(String id) {
        return Patient.builder()
                .id(id)
                .email(this.email)
                .name(this.name)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .birthDate(this.birthDate)
                .numSus(this.numSus)
                .build();
    }
}
