package br.com.amparo.backend.exception;

public class PatientUpdateException extends RuntimeException{
    public String email;
    public String name;
    public String cellphone;

    public PatientUpdateException(String email, String name, String cellphone, Exception e) {
        super(e);
        this.email = email;
        this.name = name;
        this.cellphone = cellphone;
    }
}
