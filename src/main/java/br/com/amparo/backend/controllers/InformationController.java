package br.com.amparo.backend.controllers;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.ErrorMessage;
import br.com.amparo.backend.dto.information.InformationFindResponse;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.dto.information.InformationToUpdateResponse;
import br.com.amparo.backend.service.InformationService;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.Optional;

@RestController
@RequestMapping("/information")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "12. Information")

public class InformationController {

    @Autowired
    private final InformationService informationService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Create Information",
            description = "Create a new information",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Information created successfully",
                            content = @Content(schema = @Schema(implementation = InformationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    public ResponseEntity<?> create(@RequestBody Information information, @PathVariable String id) throws IllegalAccessException {
        try {
            return new ResponseEntity<>(informationService.create(information, id), HttpStatus.CREATED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ErrorMessage("Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    @Operation(
            summary = "List Information",
            description = "List all information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Information retrieved successfully",
                            content = @Content(schema = @Schema(implementation = InformationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    public ResponseEntity<List<InformationFindResponse>> findAll() {
        return ResponseEntity.ok(informationService.findAll());

    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    @Operation(
            summary = "Find Information by Title",
            description = "Find information by title",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Information retrieved successfully",
                            content = @Content(schema = @Schema(implementation = InformationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    public ResponseEntity<List<InformationFindResponse>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(informationService.findByTitle(title));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    @Operation(
            summary = "Find Information by Id",
            description = "Find information by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Information retrieved successfully",
                            content = @Content(schema = @Schema(implementation = InformationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    public Optional<InformationResponse> findInformationById(@PathVariable String id) {
        return informationService.findInformationById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public Optional<InformationResponse> updateInformation(@PathVariable String id, @RequestBody InformationToUpdateResponse informationRequest) {
        return informationService.updateInformation(id, informationRequest);
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Doctor Information",
            description = "Doctor all information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Information retrieved successfully",
                            content = @Content(schema = @Schema(implementation = InformationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    public ResponseEntity<List<InformationFindResponse>> findAllDoctor() {
        return ResponseEntity.ok(informationService.findAllDoctor());

    }

}
