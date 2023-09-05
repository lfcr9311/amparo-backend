package br.com.amparo.backend.dto;

import lombok.Getter;

@Getter
public class CreateDoctorRequest extends CreateUserRequest{
    private String crm;
    private String uf;

    public CreateDoctorRequest() {
        super(UserType.DOCTOR);
    }

}
