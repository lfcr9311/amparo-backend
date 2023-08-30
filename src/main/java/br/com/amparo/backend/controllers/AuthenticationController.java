package br.com.amparo.backend.controllers;

import br.com.amparo.backend.DTO.AuthenticationDto;
import br.com.amparo.backend.domain.record.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto dto) {
        var password = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        var auth = authenticationManager.authenticate(password);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

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
