package br.com.amparo.backend.configuration;

<<<<<<< HEAD
import br.com.amparo.backend.service.CryptographyService;
import br.com.amparo.backend.service.impl.CryptographyServiceImpl;
=======
import br.com.amparo.backend.configuration.security.AmparoSecurityConfiguration;
import br.com.amparo.backend.repository.UserTokenRepository;
import br.com.amparo.backend.service.security.AuthService;
import br.com.amparo.backend.service.security.TokenService;
>>>>>>> 9d449fb9b1d08d16b97ceb2bcab48bfe8bfc834c
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.NoSuchAlgorithmException;

@Configuration
@Import(AmparoSecurityConfiguration.class)
public class AmparoConfiguration {
    @Bean
    public CryptographyService cryptographyService() {
        return new CryptographyServiceImpl();
    }

    @Bean
    public UserTokenRepository userTokenRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserTokenRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public AuthService loginService(TokenService tokenService, UserTokenRepository userTokenRepository)
            throws NoSuchAlgorithmException {
        return new AuthService(tokenService, userTokenRepository);
    }
}