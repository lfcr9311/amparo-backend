package br.com.amparo.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    protected String email;
    protected String name;
    protected String password;
    protected String profilePicture;
    protected String cellphone;
    private UserType userType;

    public CreateUserRequest(UserType userType) {
        this.userType = userType;
    }
}
