package br.com.amparo.backend.configuration;

import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.domain.exception.ErrorResponse;
import br.com.amparo.backend.exception.CreationException;
import br.com.amparo.backend.exception.DoctorOperationException;
import br.com.amparo.backend.exception.MedicineOperationException;
import br.com.amparo.backend.exception.PatientOperationException;
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

    @ExceptionHandler(PatientOperationException.class)
    public ResponseEntity<ErrorResponse> handlePatientOperationException(PatientOperationException e) {
        String errorMessage = "Error to modify patient " +
                "Email: " + e.getEmail() +
                ", CPF: " + e.getCpf()   +
                " "       + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorOperationException.class)
    public ResponseEntity<ErrorResponse> handleDoctorModificationException(DoctorOperationException e) {
        String errorMessage = "Error to modify doctor " +
                "Email: " + e.getEmail() +
                ", CRM: " + e.getCrm() +
                ", UF: " + e.getUf() + " " + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorOperationException.class)
    public ResponseEntity<ErrorResponse> handleDoctorCreationException(DoctorOperationException e) {
        String errorMessage = "Error while creating doctor " +
                "Email: " + e.getEmail() +
                ", CRM: " + e.getCrm() +
                ", UF: " + e.getUf() + " " + e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicineOperationException.class)
    public ResponseEntity<ErrorResponse> handleMedicineFindException(MedicineOperationException e) {
        String errorMessage = "Error to modify medication " +
                "Id: "     + e.getId()      +
                ", Name: " + e.getName()    +
                " "        + e.getMessage();                ;

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreationException.class)
    public ResponseEntity<ErrorResponse> handleCreationException(CreationException e) {
        String errorMessage = "Error to create entity: id: " + e.getIdentifier() + " Error: " + e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
