package br.com.amparo.backend.exception;

import lombok.Getter;
@Getter
public class UserOperationException extends RuntimeException {
        public String email;
        public String name;
        public String cellphone;

        public UserOperationException(String email, String name, String cellphone, Exception e) {
            super(e);
            this.email = email;
            this.name = name;
            this.cellphone = cellphone;
        }
}
