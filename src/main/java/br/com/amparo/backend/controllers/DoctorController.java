package br.com.amparo.backend.controllers;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "3. Doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> findDoctorById(@PathVariable String id) {
        return doctorService.findDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping
    public ResponseEntity<?> getDoctor() {
        String userId = SecurityUtils.getCurrentUserId();
        return doctorService.findDoctorById(userId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping
    public ResponseEntity<?> editDoctor(@RequestBody @Valid DoctorToUpdateRequest doctor) {
        return doctorService.editDoctor(doctor, SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
