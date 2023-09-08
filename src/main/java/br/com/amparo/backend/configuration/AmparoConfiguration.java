package br.com.amparo.backend.configuration;

import br.com.amparo.backend.configuration.security.AmparoSecurityConfiguration;
import br.com.amparo.backend.repository.UserTokenRepository;
import br.com.amparo.backend.service.impl.CryptographyServicePlain;
import br.com.amparo.backend.service.security.AuthService;
import br.com.amparo.backend.service.security.TokenService;
import br.com.amparo.backend.service.CryptographyService;
import br.com.amparo.backend.service.impl.CryptographyServiceSha256;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Configuration
@Import(AmparoSecurityConfiguration.class)
public class AmparoConfiguration {

    @Value("${api.security.token.secret:secret}")
    private String tokenSecuritySecret;

    @Bean
    public TokenService tokenService() {
        return new TokenService(tokenSecuritySecret);
    }

    @Bean
    public CryptographyService cryptographyService() {
        return new CryptographyServiceSha256(new Random());
    }

    @Bean
    public UserTokenRepository userTokenRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserTokenRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public AuthService loginService(TokenService tokenService, UserTokenRepository userTokenRepository,
                                    CryptographyService cryptographyService) {
        return new AuthService(tokenService, userTokenRepository, cryptographyService);
    }
}