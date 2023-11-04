package br.com.amparo.backend.configuration;

import br.com.amparo.backend.configuration.security.ValidationError;
import br.com.amparo.backend.dto.ObjectMappingError;
import br.com.amparo.backend.domain.exception.ErrorResponse;
import br.com.amparo.backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ValidationError> errors = fieldErrors
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ObjectMappingError(errors));
    }

    @ExceptionHandler(PatientOperationException.class)
    public ResponseEntity<ErrorResponse> handlePatientOperationException(PatientOperationException e) {
        String errorMessage = "Erro ao modificar o paciente " +
                "Email: " + e.getEmail() +
                ", CPF: " + e.getCpf()   +
                " "       + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorModificationException.class)
    public ResponseEntity<ErrorResponse> handleDoctorModificationException(DoctorModificationException e) {
        String errorMessage = "Erro ao modificar o médico " +
                "Email: " + e.getEmail() +
                ", CRM: " + e.getCrm()   +
                ", UF: "  + e.getUf()    +
                " "       + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorCreationException.class)
    public ResponseEntity<ErrorResponse> handleDoctorCreationException(DoctorCreationException e) {
        String errorMessage = "Erro ao criar o médico" +
                "Email: " + e.getEmail() +
                ", CRM: " + e.getCrm()   +
                ", UF: "  + e.getUf()    +
                " "       + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicineOperationException.class)
    public ResponseEntity<ErrorResponse> handleMedicineFindException(MedicineOperationException e) {
        String errorMessage = "Erro ao modificar a medicação " +
                "Id: "     + e.getId()      +
                ", Name: " + e.getName()    +
                " "        + e.getMessage();                ;

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreationException.class)
    public ResponseEntity<ErrorResponse> handleCreationException(CreationException e) {
        String errorMessage = "Erro ao criar a entidade: Erro: " + e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        String errorMessage = e.getMessage() + " Não Encontrado.";
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
