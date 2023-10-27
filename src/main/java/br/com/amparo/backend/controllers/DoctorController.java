package br.com.amparo.backend.controllers;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
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

    @Operation(operationId = "findDoctorById", description = "Patient find a doctor by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor found"),
                    @ApiResponse(responseCode = "401", description = "Token is not valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A doctor with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<DoctorResponse> findDoctorById(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Doctor Id",
                    example = "a88402a4-a68f-4912-866e-6fc6f33cbfd1"
            ) String id
    ) {
        ApiUser apiUser = SecurityUtils.getApiUser();
        if (apiUser.isDoctor() && !Objects.equals(SecurityUtils.getApiUser().getId(), id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return doctorService.findDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "getDoctor", description = "Doctor find a doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor found"),
                    @ApiResponse(responseCode = "401", description = "Token is not valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A doctor with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/crm/{crm}/uf/{uf}")
    public ResponseEntity<DoctorResponse> findByCrm(
            @PathVariable
            @Parameter(
                    name = "crm",
                    description = "Doctor CRM",
                    example = "123456"
            ) String crm,
            @PathVariable
            @Parameter(
                    name = "uf",
                    description = "Doctor UF",
                    example = "SP"
            ) String uf
    ) {
        return doctorService.findByCrm(crm, uf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping()
    public ResponseEntity<DoctorResponse> getDoctor() {
        String userId = SecurityUtils.getCurrentUserId();
        return doctorService.findDoctorById(userId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "editDoctor", description = "Edit a doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Altered doctor"),
                    @ApiResponse(responseCode = "401", description = "Token is not valid",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A doctor with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping
    public ResponseEntity<DoctorResponse> editDoctor(@RequestBody @Valid DoctorToUpdateRequest doctor) {
        return doctorService.editDoctor(doctor, SecurityUtils.getApiUser().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
