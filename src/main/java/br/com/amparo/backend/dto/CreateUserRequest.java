package br.com.amparo.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
    protected String email;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z].* [A-Z][a-z].*")
    protected String name;
    @NotNull
    protected String password;
    @NotNull
    protected String cellphone;
    @NotNull
    private UserType userType;

    protected String profilePicture;


    public CreateUserRequest(UserType userType) {
        this.userType = userType;
    }
}
