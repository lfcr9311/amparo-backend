package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ControllerAdvice
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

    @PostMapping("/profilePicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestBody Map<String, String> body) {
        return patientService.uploadProfilePicture(body.get("cpf"), body.get("profilePicture"))
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
