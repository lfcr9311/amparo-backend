package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class CreationException extends RuntimeException{
    public CreationException(Exception e) {
        super(e);
    }
}
