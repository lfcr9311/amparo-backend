package br.com.amparo.backend.exception;

public class PatientCreationException extends CreationException {
    public PatientCreationException(String cpf) {
        super(cpf);
    }
}
