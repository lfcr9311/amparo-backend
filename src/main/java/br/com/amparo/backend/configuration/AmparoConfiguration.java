package br.com.amparo.backend.configuration;

import br.com.amparo.backend.service.CryptographyService;
import br.com.amparo.backend.service.impl.CryptographyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmparoConfiguration {
    @Bean
    public CryptographyService cryptographyService() {
        return new CryptographyServiceImpl();
    }

}