package br.com.amparo.backend.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "4. TestRoute")
public class TestRouteController {

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient")
    public ResponseEntity<?> patient() {
        return ResponseEntity.ok(Map.of("message", "Hello patient"));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor")
    public ResponseEntity<?> doctor() {
        return ResponseEntity.ok(Map.of("message", "Hello doctor"));
    }
}