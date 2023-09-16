package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class DoctorCreationException extends RuntimeException {
    public String email;
    public String crm;
    public String uf;

    public DoctorCreationException(String email, String crm, String uf) {
        this.email = email;
        this.crm = crm;
        this.uf = uf;
    }


}
