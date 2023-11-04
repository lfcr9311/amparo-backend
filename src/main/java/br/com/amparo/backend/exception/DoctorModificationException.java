package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class DoctorModificationException extends RuntimeException {
    private String email;
    private String crm;
    private String uf;

    public DoctorModificationException(String email, String crm, String uf, Exception e) {
        super(e);
        this.email = email;
        this.crm = crm;
        this.uf = uf;
    }
}
