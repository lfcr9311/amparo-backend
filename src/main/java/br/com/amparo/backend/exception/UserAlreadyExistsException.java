package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private String email;
    public UserAlreadyExistsException(String email) {
        this.email = email;
    }
}
