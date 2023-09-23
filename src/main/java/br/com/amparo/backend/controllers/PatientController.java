package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.dto.PatientToUpdateRequest;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCPF(@PathVariable String cpf) {
        return patientService.findPatientByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public ResponseEntity<?> findById() {
        return patientService.findPatientById(SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping()
    public ResponseEntity<?> editPatient(@RequestBody PatientToUpdateRequest patient) {
        if (!patient.isValid()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return patientService.editPatient(patient, SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldMappedError> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(it -> new FieldMappedError(it.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ObjectMappingError(errors));
    }
}
