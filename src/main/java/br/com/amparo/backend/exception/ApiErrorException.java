package br.com.amparo.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorException extends RuntimeException {

    private static final long serialVersionUID = 1321815024950356796L;

    private final String messageError;
    private final HttpStatus httpStatus;

    public ApiErrorException(final String mensagem, final HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
        messageError = null;
    }

    public ApiErrorException(final String mensagem, final HttpStatus httpStatus, final String messageError) {
        super(mensagem);
        this.httpStatus = httpStatus;
        this.messageError = messageError;
    }
}