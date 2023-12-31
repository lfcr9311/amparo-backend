package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.dto.patient.PatientToUpdateRequest;
import br.com.amparo.backend.service.PatientService;
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

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "2. Patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Operation(operationId = "findByCPF", description = "Doctor find a patient by CPF",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Patient found"),
                    @ApiResponse(responseCode = "401", description = "Token is not Valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A patient with the specified CPF was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PatientResponse> findByCPF(
            @PathVariable
            @Parameter(
                    name = "cpf",
                    description = "Patient CPF",
                    example = "06073525049"
            ) String cpf
    ) {
        return patientService.findPatientByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "findById", description = "Find a patient by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Patient found"),
                    @ApiResponse(responseCode = "401", description = "Token is not Valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A patient with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> findById() {
        String userId = SecurityUtils.getApiUser().getId();
        return patientService.findPatientById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "editPatient", description = "Edit a patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Altered patient"),
                    @ApiResponse(responseCode = "401", description = "Token is not Valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A patient with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping
    public ResponseEntity<PatientResponse> editPatient(@RequestBody @Valid PatientToUpdateRequest patient) {
        return patientService.editPatient(patient, SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
