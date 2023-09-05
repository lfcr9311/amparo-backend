package br.com.amparo.backend.exception;

import lombok.Getter;
@Getter
public class UserCreationException extends RuntimeException {
        public String email;
        public String name;
        public String cellphone;

        public UserCreationException(String email, String name, String cellphone) {
            this.email = email;
            this.name = name;
            this.cellphone = cellphone;
        }
}
