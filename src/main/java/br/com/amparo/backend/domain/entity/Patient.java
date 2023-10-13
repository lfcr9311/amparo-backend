package br.com.amparo.backend.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {
    private String cpf;
    private String birthDate;

    @Builder
    public Patient(String id, String email, String password, String salt, String name, String cellphone,
                   String profilePicture, boolean isAnonymous, String cpf, String birthDate) {
        super(id, email, password, salt, name, cellphone, profilePicture, isAnonymous);
        this.cpf = cpf;
        this.birthDate = birthDate;
    }
}
