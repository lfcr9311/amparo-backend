package br.com.amparo.backend.dto.patient;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreatePatientRequest extends CreateUserRequest {
    @NotNull
    @Pattern(regexp = "[0-9]{11}", message = "Cpf deve conter somente 11 números")
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
