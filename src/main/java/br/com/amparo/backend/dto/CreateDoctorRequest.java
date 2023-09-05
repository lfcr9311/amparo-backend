package br.com.amparo.backend.dto;

public class CreateDoctorRequest extends CreateUserRequest{
    private String crm;
    private String uf;

    public CreateDoctorRequest() {
        super(UserType.DOCTOR);
    }

    public String getCrm() {
        return crm;
    }

    public String getUf() {
        return uf;
    }
}
