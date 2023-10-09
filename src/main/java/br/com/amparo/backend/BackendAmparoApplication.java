package br.com.amparo.backend;

import br.com.amparo.backend.configuration.AmparoConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AmparoConfiguration.class)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerToken", scheme = "bearer", bearerFormat = "jwt",
        in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Amparo Api", version = "0.0.1"),
        security = {@SecurityRequirement(name = "bearerToken")})
public class BackendAmparoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendAmparoApplication.class, args);
    }
}