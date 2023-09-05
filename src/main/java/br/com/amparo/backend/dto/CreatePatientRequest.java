package br.com.amparo.backend.dto;

import lombok.Getter;

@Getter
public class CreatePatientRequest extends CreateUserRequest{
    private String cpf;

    public CreatePatientRequest() {
        super(UserType.PATIENT);
    }
}
