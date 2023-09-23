package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class CreationException extends RuntimeException{
    private final String identifier;

    public CreationException(String identifier) {
        this.identifier = identifier;
    }
}
