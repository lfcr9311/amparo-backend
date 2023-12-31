package br.com.amparo.backend;

import com.github.dockerjava.api.command.CreateContainerCmd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestBackendAmparoApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:12.3"));
	}

	public static void main(String[] args) {
		SpringApplication.from(BackendAmparoApplication::main).with(TestBackendAmparoApplication.class).run(args);
	}

}
