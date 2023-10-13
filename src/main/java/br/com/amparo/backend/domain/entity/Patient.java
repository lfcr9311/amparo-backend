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
    private String birth_date;
    private String num_sus;

    @Builder
    public Patient(String id, String email, String password, String salt, String name, String cellphone,
                   String profilePicture, boolean isAnonymous, String cpf, String birth_date, String num_sus) {
        super(id, email, password, salt, name, cellphone, profilePicture, isAnonymous);
        this.cpf = cpf;
        this.birth_date = birth_date;
        this.num_sus = num_sus;
    }
}
