package br.com.amparo.backend.controllers;

import br.com.amparo.backend.DTO.LoginRequest;
import br.com.amparo.backend.domain.record.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.login.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest)
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElseGet(() -> new ResponseEntity<>(
                        Map.of("message", "email or password invalid"), HttpStatus.UNAUTHORIZED)
                );
    }

    //@Todo isso é responsabilidade da squad 2, a gente não precisa implementar
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        if (repository.findByLogin(dto.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(dto.password());
        repository.save(new User(dto.login(), encodedPassword, dto.role()));

        return ResponseEntity.ok().build();
    }
}
