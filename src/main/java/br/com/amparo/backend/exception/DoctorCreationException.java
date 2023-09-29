package br.com.amparo.backend.exception;

public class DoctorCreationException extends CreationException{
    public DoctorCreationException(String crm, String uf) {
        super(uf + " " + crm);
    }
}
