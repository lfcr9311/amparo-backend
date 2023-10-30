package br.com.amparo.backend.exception;

public class PatientCreationException extends CreationException {
    private final String cpf;
    private final String email;
    public PatientCreationException(Exception e, String cpf, String email) {
        super(e);
        this.cpf = cpf;
        this.email = email;
    }
}
