package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class PatientCreationException extends RuntimeException {
    public String cpf;
    public PatientCreationException(String cpf) {
        this.cpf = cpf;
    }
}
