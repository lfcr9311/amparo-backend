package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class DoctorOperationException extends RuntimeException {
    public String email;
    public String crm;
    public String uf;

    public DoctorOperationException(String email, String crm, String uf, Exception e) {
        super(e);
        this.email = email;
        this.crm = crm;
        this.uf = uf;
    }

    public DoctorOperationException(String email, String crm, String uf) {
        this.email = email;
        this.crm = crm;
        this.uf = uf;
    }
}
