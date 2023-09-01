package br.com.amparo.backend.controllers;

import br.com.amparo.backend.DTO.LoginRequest;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.repository.UserTokenRepository;
import br.com.amparo.backend.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestRouteController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository repository;

    @GetMapping("/patient")
    public ResponseEntity<?> patientLogin() {

    }
}
