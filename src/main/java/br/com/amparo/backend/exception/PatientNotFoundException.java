package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String identifier){
        super(identifier);
    }
}
