package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.FieldMappedError;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.dto.DoctorToUpdateRequest;
import br.com.amparo.backend.dto.PatientToUpdateRequest;
import br.com.amparo.backend.service.DoctorService;
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
@RequestMapping("/doctor")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findDoctorById(@PathVariable UUID id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (!Objects.equals(SecurityUtils.getApiUser().getId(), id.toString())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return doctorService.findDoctorById(id.toString())
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping()
    public ResponseEntity<?> editDoctor(@RequestBody DoctorToUpdateRequest doctor) {
        if (!doctor.isValid()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return doctorService.editDoctor(doctor, SecurityUtils.getApiUser().getId())
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
