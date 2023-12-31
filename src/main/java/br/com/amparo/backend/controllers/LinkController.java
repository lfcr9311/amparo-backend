package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.service.LinkService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "7. link controller")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Operation(operationId = "requestDoctorToPatient", description = "Patient request a link to a doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful request",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "500", description = "Request failed",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "409", description = "Request already exists",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/to/doctor/{doctorId}")
    public ResponseEntity<?> requestDoctorToPatient(
            @PathVariable
            @Parameter(
                    name = "doctorId",
                    description = "Doctor Id",
                    example = "a88402a4-a68f-4912-866e-6fc6f33cbfd1"
            ) String doctorId
    ) {
         if (linkService.checkConnection(doctorId, SecurityUtils.getApiUser().getId())) {
             return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
            return linkService.requestDoctorToPatient(doctorId, SecurityUtils.getApiUser().getId())
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Operation(operationId = "linkDoctorToPatient", description = "Link a doctor to a patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor and patient linked",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "500", description = "Linking failed",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "409", description = "Link already exists",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/to/patient/{patientId}")
    public ResponseEntity<?> linkDoctorToPatient(
            @PathVariable
            @Parameter(
                    name = "patientId",
                    description = "Patient Id",
                    example = "a88402a4-a68f-4912-866e-6fc6f33cbfd1"
            ) String patientId
    ) {
        if (linkService.checkConnectionRequest(SecurityUtils.getApiUser().getId(), patientId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
            return linkService.linkDoctorToPatient(SecurityUtils.getApiUser().getId(), patientId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(operationId = "deleteLinkPatient", description = "Delete a link with patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Link deleted",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "500", description = "Delete link failed",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping ("/patient/{patientId}")
    public ResponseEntity<?> deleteLinkPatient(
            @PathVariable
            @Parameter(
                name = "patientId",
                description = "Patient Id",
                example = "a88402a4-a68f-4912-866e-6fc6f33cbfd1"
            ) String patientId
    ) {
        return linkService.deleteLinkPatient(SecurityUtils.getApiUser().getId(), patientId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(operationId = "deleteLinkDoctor", description = "Delete a link with doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Link deleted",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "500", description = "Delete link failed",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping ("/doctor/{doctorId}")
    public ResponseEntity<?> deleteLinkDoctor(
            @PathVariable
            @Parameter(
                name = "doctorId",
                description = "Doctor Id",
                example = "a88402a4-a68f-4912-866e-6fc6f33cbfd1"
            ) String doctorId
    ) {
        return linkService.deleteLinkDoctor(SecurityUtils.getApiUser().getId(), doctorId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/doctor")
    @Operation(description = "View all doctors of a patient")
    public ResponseEntity<List<DoctorResponse>> getAllLinked() {
        List<DoctorResponse> doctors = linkService.getAllDoctorOfPatient(SecurityUtils.getApiUser().getId());
        return ResponseEntity.ok(doctors);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/pacient")
    @Operation(description = "View all patient of a doctor")
    public ResponseEntity<List<PatientResponse>> getAllPatientLinked() {
        List<PatientResponse> patients = linkService.getAllPatientOfDoctor(SecurityUtils.getApiUser().getId());
        return ResponseEntity.ok(patients);
    }
}
