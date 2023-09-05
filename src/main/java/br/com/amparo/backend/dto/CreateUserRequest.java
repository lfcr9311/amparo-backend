package br.com.amparo.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "userType"
)
@JsonSubTypes(
        value = {
                @JsonSubTypes.Type(value = CreatePatientRequest.class, name = "PATIENT")
        }
)
public abstract class CreateUserRequest{
    private String email;
    private String name;
    private String password;
    private String profilePicture;
    private String cellphone;
    private UserType userType;

    public CreateUserRequest(UserType userType) {
        this.userType = userType;
    }

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }



    public String getProfilePicture() {
        return profilePicture;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getPassword() {
        return password;
    }

}
