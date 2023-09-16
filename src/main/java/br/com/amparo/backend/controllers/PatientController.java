package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.PatientToUpdateRequest;
import br.com.amparo.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ControllerAdvice
@CrossOrigin("*")
@Slf4j

public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCPF(@PathVariable String cpf) {
        return patientService.findPatientByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return patientService.findPatientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/editPatient")
    public ResponseEntity<?> editPatient(@RequestHeader Map<String, String> headers, @RequestBody PatientToUpdateRequest patient) {
        log.info("Headers: {}", headers);
        String token = headers.get("authorization");
        return patientService.editPatient(patient)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
