package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import br.com.amparo.backend.service.DosageService;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dosage")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "8. Dosage")
public class DosageController {
    @Autowired
    private DosageService service;

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/{medicineId}")
    @Operation(
            summary = "Add Dosage",
            description = "Add a new dosage for a specific medicine",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dosage added successfully",
                            content = @Content(schema = @Schema(implementation = DosageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    public ResponseEntity<?> addDosage(@PathVariable String medicineId, @RequestBody AddDosageRequest request) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Patient not found"), HttpStatus.NOT_FOUND);
        }
        Optional<DosageResponse> dosage = service.addDosage(patientId, medicineId, request);
        if (service.addDosage(patientId, medicineId, request).isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Bad request"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dosage, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/{dosageId}")
    @Operation(
            summary = "Update Dosage",
            description = "Update an existing dosage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dosage updated successfully",
                            content = @Content(schema = @Schema(implementation = DosageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient or dosage not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    public ResponseEntity<?> updateDosage(@PathVariable String dosageId, @RequestBody EditDosageRequest request) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Patient not found"),HttpStatus.NOT_FOUND);
        }
        if (!patientId.equals(service.getDosage(dosageId).get().id_patient())) {
            return new ResponseEntity<>(new ErrorMessage("Forbidden"),HttpStatus.FORBIDDEN);
        }
        Optional<DosageResponse> dosage = service.editDosage(patientId, dosageId, request);
        if (dosage.isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Dosage not found"),HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dosage, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{dosageId}")
    @Operation(
            summary = "Get Dosage",
            description = "Get details of a specific dosage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dosage retrieved successfully",
                            content = @Content(schema = @Schema(implementation = DosageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient or dosage not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    public ResponseEntity<?> getDosage(@PathVariable String dosageId) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Patient not found"),HttpStatus.NOT_FOUND);
        }

        Optional<DosageResponse> dosage = service.getDosage(dosageId);

        if(dosage.isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Dosage not found"),HttpStatus.NOT_FOUND);
        } else if (!patientId.equals(dosage.get().id_patient())) {
            return new ResponseEntity<>(new ErrorMessage("Forbidden"),HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(dosage.get(), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/{dosageId}")
    @Operation(
            summary = "Delete Dosage",
            description = "Delete a dosage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dosage deleted successfully",
                            content = @Content(schema = @Schema(implementation = DosageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient or dosage not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    public ResponseEntity<?> deleteDosage(@PathVariable String dosageId) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Patient not found"),HttpStatus.NOT_FOUND);
        }

        Optional<DosageResponse> dosage = service.getDosage(dosageId);

        if (dosage.isEmpty()) {
            return new ResponseEntity<>(new ErrorMessage("Dosage not found"),HttpStatus.NOT_FOUND);
        } else if (!patientId.equals(dosage.get().id_patient())) {
            return new ResponseEntity<>(new ErrorMessage("Forbidden"),HttpStatus.FORBIDDEN);
        } else {
            if (service.deleteDosage(dosage.get()).isEmpty()) {
                return new ResponseEntity<>(new ErrorMessage("Error deleting dosage"),HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(dosage.get(), HttpStatus.OK);
            }
        }
    }
}
