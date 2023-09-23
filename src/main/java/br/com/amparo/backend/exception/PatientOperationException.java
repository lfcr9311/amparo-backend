package br.com.amparo.backend.exception;

public class PatientOperationException extends RuntimeException{
    public String email;
    public String name;
    public String cellphone;

    public PatientOperationException(String email, String name, String cellphone, Exception e) {
        super(e);
        this.email = email;
        this.name = name;
        this.cellphone = cellphone;
    }
}
