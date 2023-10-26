package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.medicine.MedicineResponse;
import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineIncompatibilityRequest;
import br.com.amparo.backend.service.MedicineService;
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
@RequestMapping("/medicine")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "6. Medicine")
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @Operation(operationId = "findMedicineById", description = "Find a medicine by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Medicine found"),
                    @ApiResponse(responseCode = "404", description = "A medicine with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<MedicineResponse> findById(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Medicine Id",
                    example = "0"
            ) int id
    ) {
        return medicineService.findMedicineById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "findMedicineByName", description = "Find a medicine by Name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Medicine found"),
                    @ApiResponse(responseCode = "404", description = "A medicine with the specified name was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<MedicineResponse> findByName(
            @PathVariable
            @Parameter(
                    name = "name",
                    description = "Medicine Name",
                    example = "Ibuprofen"
            ) String name
    ) {
        return medicineService.findMedicineByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "findAllMedicines", description = "Find all medicines",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Medicines found"),
                    @ApiResponse(responseCode = "404", description = "Medicines not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/all")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<List<MedicineResponse>> findAllMedicines (
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
        return ResponseEntity.ok(medicineService.findAllMedicines(pageNumber, pageSize));
    }

    @Operation(operationId = "findAllIncompatibility", description = "Find a medicine incompatibilities",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Incompatibilities found"),
                    @ApiResponse(responseCode = "404", description = "Incompatibilities not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @GetMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<List<MedicineIncResponse>> findAllIncompatibility(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Medicine Id",
                    example = "0"
            ) int id
    ) {
        List<MedicineIncResponse> incompatibilities = medicineService.findAllIncompatibility(id);
        if (incompatibilities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(incompatibilities);
        }
    }

    @Operation(operationId = "findIncompatibility", description = "Add a medicine incompatibility",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Incompatibilities added"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "Incompatibilities not added",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PostMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<List<MedicineIncResponse>> findIncompatibility(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Medicine Id",
                    example = "0"
            ) int id,
            @RequestBody MedicineIncompatibilityRequest request
    ) {
        if (request.medicines().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MedicineIncResponse> incompatibilities = medicineService.findIncompatibility(id, request.medicines());
        if (incompatibilities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(incompatibilities);
        }
    }
}