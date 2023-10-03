package br.com.amparo.backend.configuration;

import br.com.amparo.backend.configuration.security.AmparoSecurityConfiguration;
import br.com.amparo.backend.repository.*;
import br.com.amparo.backend.service.*;
import br.com.amparo.backend.service.impl.*;
import br.com.amparo.backend.service.impl.DoctorServiceImpl;
import br.com.amparo.backend.service.impl.ExamServiceImpl;
import br.com.amparo.backend.service.impl.MedicineServiceImpl;
import br.com.amparo.backend.service.impl.ExamServiceImpl;
import br.com.amparo.backend.service.impl.PatientServiceImpl;
import br.com.amparo.backend.service.impl.UserServiceImpl;
import br.com.amparo.backend.service.security.AuthService;
import br.com.amparo.backend.service.security.CryptographyServiceSha256;
import br.com.amparo.backend.service.security.TokenService;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public PatientService patientService(UserService userService, PatientRepository patientRepository) {
        return new PatientServiceImpl(userService, patientRepository);
    }

    @Bean
    public DoctorService doctorService(UserService userService, DoctorRepository doctorRepository) {
        return new DoctorServiceImpl(userService, doctorRepository);
    }

    @Bean
    public MedicineService medicineService(MedicineRepository medicineRepository) {
        return new MedicineServiceImpl(medicineRepository);
    }

    @Bean
    public UserTokenRepository userTokenRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserTokenRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public PatientRepository patientRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PatientRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ExamService examService(ExamRepository examRepository, PatientRepository patientRepository) {
        return new ExamServiceImpl(examRepository, patientRepository);
    }

    @Bean
    public ExamRepository examRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new ExamRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public DoctorRepository doctorRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new DoctorRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public UserRepository userRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public MedicineRepository medicineRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MedicineRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public LinkRepository linkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new LinkRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public LinkService linkService(LinkRepository linkRepository) {
        return new LinkServiceImpl(linkRepository);
    }

    @Bean
    public MedicineRepository medicineRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MedicineRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public AuthService loginService(TokenService tokenService, UserTokenRepository userTokenRepository,
                                    CryptographyService cryptographyService, UserRepository userRepository,
                                    PatientRepository patientRepository, DoctorRepository doctorRepository) {

        return new AuthService(tokenService, userTokenRepository, cryptographyService,
                userRepository, patientRepository, doctorRepository);
    }
}