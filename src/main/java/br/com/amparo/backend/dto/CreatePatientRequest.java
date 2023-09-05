package br.com.amparo.backend.dto;

public class CreatePatientRequest extends CreateUserRequest{
    private String cpf;

    public CreatePatientRequest() {
        super(UserType.PATIENT);
    }
    public String getCpf() {
        return cpf;
    }
}
