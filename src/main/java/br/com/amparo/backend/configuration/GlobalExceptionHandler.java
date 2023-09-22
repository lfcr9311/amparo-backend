package br.com.amparo.backend.configuration;

import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.domain.exception.ErrorResponse;
import br.com.amparo.backend.exception.ApiErrorException;
import br.com.amparo.backend.exception.PatientUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldMappedError> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(it -> new FieldMappedError(it.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ObjectMappingError(errors));
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErrorResponse> handleApiErrorException(ApiErrorException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        String errorMessage = e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(PatientUpdateException.class)
    public ResponseEntity<ErrorResponse> handlePatientUpdateException(PatientUpdateException e) {
        String errorMessage = "Erro ao atualizar paciente com os seguintes detalhes: " +
                "Email: " + e.email +
                ", Nome: " + e.name +
                ", Celular: " + e.cellphone;

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
