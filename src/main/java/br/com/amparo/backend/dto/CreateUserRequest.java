package br.com.amparo.backend.dto;

import br.com.amparo.backend.dto.doctor.CreateDoctorRequest;
import br.com.amparo.backend.dto.patient.CreatePatientRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "userType"
)
@JsonSubTypes(
        value = {
                @JsonSubTypes.Type(value = CreatePatientRequest.class, name = "PATIENT"),
                @JsonSubTypes.Type(value = CreateDoctorRequest.class, name = "DOCTOR")
        }
)
@Getter
@NoArgsConstructor
public abstract class CreateUserRequest{

    @Schema(example = "email@email.com.br")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email pattern")
    @Setter
    protected String email;
    @NotBlank(message = "Name should not be blank")
    @Schema(example = "Theo")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]{2,100}$", message = "Invalid name pattern")
    protected String name;
    @NotBlank(message = "password is required")
    protected String password;
    protected String cellphone;
    @NotNull(message = "Required to know what type of user: PATIENT or DOCTOR")
    private UserType userType;
    protected String profilePicture;

    public CreateUserRequest(UserType userType) {
        this.userType = userType;
    }
}
