package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class DoctorOperationException extends RuntimeException {
    private String email;
    private String crm;
    private String uf;

    public DoctorOperationException(String email, String crm, String uf, Exception e) {
        super(e);
        this.email = email;
        this.crm = crm;
        this.uf = uf;
    }
}