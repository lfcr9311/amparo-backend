package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class PatientOperationException extends RuntimeException{
    private String email;
    private String cpf;

    public PatientOperationException(String email, String cpf, Exception e) {
        super(e);
        this.email = email;
        this.cpf = cpf;
    }
}
