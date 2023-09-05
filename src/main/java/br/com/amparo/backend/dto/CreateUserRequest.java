package br.com.amparo.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Email;
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
    @Email
    @NotNull
    protected String email;
    @NotNull
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
