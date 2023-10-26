package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.dto.medicine.MedicineResponse;
import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineIncompatibilityRequest;
import br.com.amparo.backend.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
                    @ApiResponse(responseCode = "200", description = "Medicine found",
                            content = @Content(schema = @Schema(implementation = MedicineResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Medicine not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findById(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Medicine Id",
                    example = "10"
            ) int id
    ) {
        return medicineService.findMedicineById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "findMedicineByName", description = "Find a medicine by Name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Medicine found",
                            content = @Content(schema = @Schema(implementation = MedicineResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Medicine not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findByName(
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
                    @ApiResponse(responseCode = "200", description = "Medicines found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MedicineResponse.class)))
                    )
            })
    @GetMapping("/all")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findAllMedicines (
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

    @GetMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findAllIncompatibility(@PathVariable int id) {
        List<MedicineIncResponse> incompatibilities = medicineService.findAllIncompatibility(id);
        if (incompatibilities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(incompatibilities);
        }
    }

    @PostMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findIncompatibility(@PathVariable int id,
                                                 @RequestBody MedicineIncompatibilityRequest request) {
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