package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.record.PatientRegistrationData;
import br.com.amparo.backend.exception.ApiErrorException;
import br.com.amparo.backend.mapper.PatientMapper;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.amparo.backend.exception.ApiError.EMAIL_JA_EXISTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserService userService;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientRegistrationData saveNewPatient(PatientRegistrationData patientRegistrationData) {
        if (userService.alreadyExistsEmail(patientRegistrationData.user().email())) {
            throw new ApiErrorException(EMAIL_JA_EXISTE.getMessage(), BAD_REQUEST);
        }

        Patient patient = PatientMapper.MAPPER.mapToPatient(patientRegistrationData);
        Patient savedUser = patientRepository.save(patient);
        return PatientMapper.MAPPER.mapToPatientRegistrationData(savedUser);
    }
}
