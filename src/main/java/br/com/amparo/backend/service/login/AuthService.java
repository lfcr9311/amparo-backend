package br.com.amparo.backend.service.login;

import br.com.amparo.backend.DTO.LoginRequest;
import br.com.amparo.backend.repository.UserTokenRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

public class AuthService {
    private TokenService tokenService;

    private UserTokenRepository userTokenRepository;

    private MessageDigest sha256Digestor;

    public AuthService(TokenService tokenService, UserTokenRepository userTokenRepository)
            throws NoSuchAlgorithmException {
        this.tokenService = tokenService;
        this.userTokenRepository = userTokenRepository;
    }

    public Optional<String> login(LoginRequest loginRequest) {
        return userTokenRepository.findUserByEmail(loginRequest.email())
                .flatMap(userTokenEntity -> {
                    var inputPassword = loginRequest.password() + userTokenEntity.salt();
                    var encodedInputPassword = sha256Digestor.digest(inputPassword.getBytes(StandardCharsets.UTF_8));
                    if (Arrays.equals(encodedInputPassword, userTokenEntity.getBytePassword())) {
                        tokenService.generateToken(userTokenEntity)
                        //@Todo fazer o parse pro token, não colocar todos os campos do UserToken só o q for importante.
                        return Optional.of("GENERATED TOKEN");
                    } else {
                        return Optional.empty();
                    }
                });
    }
}
