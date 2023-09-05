package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.CreatePatientRequest;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.LoginRequest;
import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.controllers.dto.LoginTokenResponse;
import br.com.amparo.backend.domain.entity.UserTokenEntity;
import br.com.amparo.backend.service.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Operation(operationId = "login", description = "Generate token for user",
    responses = {
            @ApiResponse(responseCode = "200", description = "Generated Bearer token for the user",
            content = @Content(schema = @Schema(implementation = LoginTokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "email or password invalid",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest)
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElseGet(() -> new ResponseEntity<>(
                        Map.of("message", "email or password invalid"), HttpStatus.UNAUTHORIZED)
                );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest createUserRequest) {
        boolean registrationSuccessful = authService.register(createUserRequest);
        if (registrationSuccessful) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    Map.of("message", "Registration failed"), HttpStatus.BAD_REQUEST
            );
        }
    }


}
