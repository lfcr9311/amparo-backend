package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.service.DosageService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "11. DoctorDosage controller")
public class DoctorDosageController {

    @Autowired
    private DosageService dosageService;

    @Autowired
    private LinkService linkService;

    @Operation(operationId = "listAllDosagesToPatient", description = "Doctor access to a patient's dosages",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dosages found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "Dosage or patient with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/dosages/{patientId}")
    public ResponseEntity<List<DosageResponse>> listAllDosagesToPatient(
            @PathVariable
            @Parameter(
                    name = "patientId",
                    description = "Patient Id",
                    example = "aed85a2b-28b5-4d24-8c82-40ed02608402"
            ) String patientId,
            @RequestParam(defaultValue = "1")
            @Parameter(
                    name = "pageNumber",
                    description = "Current page number",
                    example = "1"
            ) int pageNumber,
            @RequestParam(defaultValue = "10")
            @Parameter(
                    name = "pageSize",
                    description = "Number of items per page",
                    example = "10"
            ) int pageSize
    ) {
        boolean connection = linkService.checkConnection(SecurityUtils.getApiUser().getId(), patientId);
        if (!connection) {
            throw new RuntimeException("Connection not found");
        }

        return ResponseEntity.ok(dosageService.listAllDosagesToPatient(patientId, pageNumber, pageSize));
    }
}
