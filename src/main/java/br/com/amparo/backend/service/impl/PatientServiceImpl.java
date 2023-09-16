package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.exception.ApiErrorException;
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
    private final UserService userService;


    @Override
    @Transactional
    public Patient saveNewPatient(Patient patientRegistrationData) {
        if (userService.alreadyExistsEmail(patientRegistrationData.getEmail())) {
            throw new ApiErrorException(EMAIL_JA_EXISTE.getMessage(), BAD_REQUEST);
        }

        return patientRegistrationData;
    }
}
