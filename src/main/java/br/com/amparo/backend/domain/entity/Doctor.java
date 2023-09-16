package br.com.amparo.backend.domain.entity;

import lombok.*;

@Getter
public class Doctor extends User {
    private String crm;
    private String uf;

    @Builder
    public Doctor(String id, String email, String password, String salt, String name, String cellphone, String profilePicture, boolean isAnonymous, String crm, String uf) {
        super(id, email, password, salt, name, cellphone, profilePicture, isAnonymous);
        this.crm = crm;
        this.uf = uf;
    }
}
