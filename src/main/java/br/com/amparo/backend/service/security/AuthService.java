package br.com.amparo.backend.service.security;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.dto.CreateUserRequest;
import br.com.amparo.backend.dto.doctor.CreateDoctorRequest;
import br.com.amparo.backend.dto.login.LoginRequest;
import br.com.amparo.backend.dto.patient.CreatePatientRequest;
import br.com.amparo.backend.exception.DoctorCreationException;
import br.com.amparo.backend.exception.PatientCreationException;
import br.com.amparo.backend.exception.UserAlreadyExistsException;
import br.com.amparo.backend.repository.DoctorRepository;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.repository.UserTokenRepository;
import br.com.amparo.backend.service.CryptographyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class AuthService {
    private DoctorRepository doctorRepository;
    private TokenService tokenService;

    private UserTokenRepository userTokenRepository;

    private CryptographyService cryptographicService;
    private UserRepository userRepository;
    private PatientRepository patientRepository;

    public AuthService(TokenService tokenService, UserTokenRepository userTokenRepository,
                       CryptographyService cryptographicService, UserRepository userRepository,
                       PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.tokenService = tokenService;
        this.userTokenRepository = userTokenRepository;
        this.cryptographicService = cryptographicService;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
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

    @Transactional
    public User register(CreateUserRequest userRequest) {
        return switch (userRequest.getUserType()) {
            case PATIENT -> registerPatient((CreatePatientRequest) userRequest);
            case DOCTOR -> registerDoctor((CreateDoctorRequest) userRequest);
        };
    }

    private Patient registerPatient(CreatePatientRequest userRequest) {
        Optional<User> userOpt = userRepository.findByEmail(userRequest.getEmail());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Patient patient = userRequest.toPatient();
        SaltedPassword passwordPatient = this.cryptographicService.encrypt(patient.getPassword());
        String id = userRepository.create(patient, passwordPatient);
        patient.setId(id);
        patientRepository.create(patient);
        return patient;
    }

    private Doctor registerDoctor(CreateDoctorRequest userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Doctor doctor = userRequest.toDoctor();
        SaltedPassword passwordDoctor = this.cryptographicService.encrypt(userRequest.getPassword());
        String id = userRepository.create(doctor, passwordDoctor);
        doctor.setId(id);
        doctorRepository.create(doctor);
        return doctor;
    }
}
