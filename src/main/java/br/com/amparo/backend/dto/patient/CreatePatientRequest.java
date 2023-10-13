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
    @Pattern(regexp = "[0-9]{11}", message = "CPF deve conter somente 11 números")
    private String cpf;

    @NotNull
    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$", message = "Data deve ser DD/MM/AAAA")
    private String birth_date;

    @Pattern(regexp = "\\d{0}|\\d{15}$", message = "Nº do SUS deve conter 15 dígitos")
    private String num_sus;

    public CreatePatientRequest() {
        super(UserType.PATIENT);
    }

    public Patient toPatient() {
        return Patient.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .cpf(this.cpf)
                .birth_date(this.birth_date)
                .num_sus(this.num_sus)
                .build();
    }
}
