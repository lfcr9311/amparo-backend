package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Patient;
import lombok.Getter;

@Getter
public class CreatePatientRequest extends CreateUserRequest{
    private String cpf;

    public CreatePatientRequest() {
        super(UserType.PATIENT);
    }

    public Patient toPatient(){
        return Patient.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .build();
    }
}
