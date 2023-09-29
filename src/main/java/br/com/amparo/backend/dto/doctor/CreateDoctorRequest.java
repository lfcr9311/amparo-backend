package br.com.amparo.backend.dto.doctor;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateDoctorRequest extends CreateUserRequest {
    @NotNull
    @Pattern(regexp = "\\d{4,6}", message = "CRM deve conter entre 4 a 6 dígitos")
    private String crm;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter somente 2 caracteres")
    private String uf;

    public CreateDoctorRequest() {
        super(UserType.DOCTOR);
    }

    public Doctor toDoctor() {
        return Doctor.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .profilePicture(this.profilePicture)
                .cellphone(this.cellphone)
                .crm(this.crm)
                .uf(this.uf)
                .build();
    }
}
