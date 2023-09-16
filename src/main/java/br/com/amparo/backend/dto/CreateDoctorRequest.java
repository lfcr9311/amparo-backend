package br.com.amparo.backend.dto;

import br.com.amparo.backend.domain.entity.Doctor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateDoctorRequest extends CreateUserRequest{
    @NotNull
    private int crm;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter somente 2 letras")
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
