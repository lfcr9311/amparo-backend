package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.service.LinkService;
import br.com.amparo.backend.service.security.SecurityUtils;
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

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "8. link controller")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Operation(operationId = "link", description = "Link a doctor to a patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor and patient linked"),
                    @ApiResponse(responseCode = "500", description = "Linking failed",
                            content = @Content(schema = @Schema(implementation = ObjectMappingError.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Link already exists")
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{id}")
    public ResponseEntity<?> linkDoctorToPatient(@PathVariable String id) {
         if (linkService.checkConnection(SecurityUtils.getApiUser().getId(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return linkService.linkDoctorToPatient(SecurityUtils.getApiUser().getId(), id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
