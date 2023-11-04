package br.com.amparo.backend.configuration;

import br.com.amparo.backend.configuration.security.AmparoSecurityConfiguration;
import br.com.amparo.backend.controllers.FileController;
import br.com.amparo.backend.controllers.InformationController;
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
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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
    @Value("${aws.region}")
    private String region;
    @Value("${aws.key}")
    private String awsKey;
    @Value("${aws.secret}")
    private String awsSecret;

    @Bean
    public TokenService tokenService() {
        return new TokenService(tokenSecuritySecret);
    }

    @Bean
    public CryptographyService cryptographyService() {
        return new CryptographyServiceSha256(new Random());
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsKey, awsSecret)))
                .build();
    }

    @Bean
    public FileService fileService(AmazonS3 amazonS3) {
        return new FileService(amazonS3);
    }

    @Bean
    public FileController fileController(FileService fileService) {
        return new FileController(fileService);
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
    public DosageRepository dosageRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new DosageRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public DosageService dosageService(DosageRepository dosageRepository, PatientService patientService) {
        return new DosageServiceImpl(dosageRepository, patientService);
    }

    @Bean
    public UserRepository userRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public LinkRepository linkRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new LinkRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public LinkService linkService(LinkRepository linkRepository, DoctorService doctorService, PatientService patientService) {
        return new LinkServiceImpl(linkRepository, doctorService, patientService);
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

    @Bean
    public InformationRepository informationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new InformationRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public InformationService informationService(InformationRepository informationRepository) {
        return new InformationServiceImpl(informationRepository);
    }
}