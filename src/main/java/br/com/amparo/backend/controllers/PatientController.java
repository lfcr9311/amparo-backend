package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.patient.PatientToUpdateRequest;
import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "2. Patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> findByCPF(@PathVariable String cpf) {
        return patientService.findPatientByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> findById() {
        String userId = SecurityUtils.getApiUser().getId();
        return patientService.findPatientById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping
    public ResponseEntity<?> editPatient(@RequestBody @Valid PatientToUpdateRequest patient) {
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
