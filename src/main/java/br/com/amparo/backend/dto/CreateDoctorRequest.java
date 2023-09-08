package br.com.amparo.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateDoctorRequest extends CreateUserRequest{
    @NotNull
    @Pattern(regexp = "[0-9]", message = "CRM deve conter somente 6 números")
    private String crm;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter somente 2 letras")
    private String uf;

    public CreateDoctorRequest() {
        super(UserType.DOCTOR);
    }

}
