package br.com.amparo.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    protected String email;
    @NotBlank(message = "Name should not be blank")
    @Pattern(regexp = "[^0-9]", message = "Invalid name pattern")
    protected String name;
    @NotBlank(message = "password is required")
    protected String password;
    @NotBlank(message = "Cellphone is required")
    protected String cellphone;
    @NotNull(message = "Required to know what type of user: PATIENT or DOCTOR")
    private UserType userType;

    protected String profilePicture;


    public CreateUserRequest(UserType userType) {
        this.userType = userType;
    }
}
