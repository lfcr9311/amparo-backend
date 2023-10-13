package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(operationId = "findDoctorById", description = "Find a doctor by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor found",
                            content = @Content(schema = @Schema(implementation = DoctorResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Doctor not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<?> findDoctorById(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Doctor Id",
                    example = "a7f6b9c0a8f0d2c4f1e9b5c8f3c6a0e2a3d9b4d1a7d3e6c5a9f8b7d0a8f1e2c4"
            )
            String id
    ) {
        ApiUser apiUser = SecurityUtils.getApiUser();
        if (apiUser.isDoctor() && !Objects.equals(SecurityUtils.getApiUser().getId(), id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return doctorService.findDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Operation(operationId = "editDoctor", description = "Edit a doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Altered doctor",
                            content = @Content(schema = @Schema(implementation = DoctorResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Doctor not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping
    public ResponseEntity<?> editDoctor(@RequestBody @Valid DoctorToUpdateRequest doctor) {
        return doctorService.editDoctor(doctor, SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
