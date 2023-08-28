package br.com.amparo.backend;

import br.com.amparo.backend.configuration.AmparoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AmparoConfiguration.class)
public class BackendAmparoApplication {
	public static void main(String[] args) {
		SpringApplication.run
				(BackendAmparoApplication.class, args);
	}
}