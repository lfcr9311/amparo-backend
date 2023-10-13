package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.controllers.dto.LoginTokenResponse;
import br.com.amparo.backend.controllers.dto.ObjectMappingError;
import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.login.LoginRequest;
import br.com.amparo.backend.dto.login.LoginResponseDto;
import br.com.amparo.backend.exception.UserAlreadyExistsException;
import br.com.amparo.backend.service.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "1. Auth controller")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Operation(operationId = "login", description = "Generate token for user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Generated Bearer token for the user",
                            content = @Content(schema = @Schema(implementation = LoginTokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Email or password invalid",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            })
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> token = authService.login(loginRequest);
        if (token.isPresent()) {
            return ResponseEntity.ok(new LoginResponseDto(token.get()));
        } else {
            return new ResponseEntity<>(new ErrorMessage("Email of password invalid"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(operationId = "register", description = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Patient or Doctor Created",
                            content = @Content(schema = @Schema(oneOf = {Patient.class, Doctor.class}))
                    ),
                    @ApiResponse(responseCode = "500", description = "Registration failed",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ObjectMappingError.class))
                    )
            })
    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserRequest createUserRequest) {
        try {
            return new ResponseEntity<>(authService.register(createUserRequest), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ignored) {
            return new ResponseEntity<>(new ErrorMessage("Registration failed for email"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(operationId = "validate", description = "Validate if the token used is valid.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Token is valid"),
                    @ApiResponse(responseCode = "401", description = "Token is not valid")
            }
    )
    @GetMapping("/valid")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> isTokenValid() {
        return ResponseEntity.noContent().build();
    }
}
