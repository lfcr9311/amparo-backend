package br.com.amparo.backend.service.security;

import br.com.amparo.backend.dto.CreateDoctorRequest;
import br.com.amparo.backend.dto.CreatePatientRequest;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.LoginRequest;
import br.com.amparo.backend.domain.entity.UserTokenEntity;
import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.repository.UserTokenRepository;
import br.com.amparo.backend.service.CryptographyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthService {
    private TokenService tokenService;

    private UserTokenRepository userTokenRepository;

    private CryptographyService cryptographicService;


    public AuthService(TokenService tokenService, UserTokenRepository userTokenRepository,
                       CryptographyService cryptographicService) {
        this.tokenService = tokenService;
        this.userTokenRepository = userTokenRepository;
        this.cryptographicService = cryptographicService;
    }

    public Optional<String> login(LoginRequest loginRequest) {
        return userTokenRepository.findUserByEmail(loginRequest.email())
                .flatMap(userTokenEntity -> {
                    if (cryptographicService.compare(loginRequest.password(), userTokenEntity.getSaltedPassword())) {
                        String token = tokenService.generateToken(userTokenEntity.toTokenUser());
                        return Optional.of(token);
                    } else {
                        return Optional.empty();
                    }
                });
    }

    public boolean register(CreateUserRequest userRequest) {
        return switch (userRequest.getUserType()) {
            case PATIENT -> registerPatient((CreatePatientRequest) userRequest);
            case DOCTOR -> registerDoctor((CreateDoctorRequest) userRequest);
        };
    }

    private boolean registerDoctor(CreateDoctorRequest userRequest) {
        return false;
    }

    private boolean registerPatient(CreatePatientRequest userRequest) {
        return true;
    }
}
