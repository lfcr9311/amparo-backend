package br.com.amparo.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient extends User {
    private String cpf;

    @Builder
    public Patient(String id, String email, String password, String salt, String name, String cellphone,
                   String profilePicture, boolean isAnonymous, String cpf) {
        super(id, email, password, salt, name, cellphone, profilePicture, isAnonymous);
        this.cpf = cpf;
    }
}
