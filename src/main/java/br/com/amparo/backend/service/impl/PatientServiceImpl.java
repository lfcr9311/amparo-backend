package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.PatientResponse;
import br.com.amparo.backend.dto.PatientToUpdateRequest;
import br.com.amparo.backend.exception.ApiErrorException;
import br.com.amparo.backend.exception.PatientUpdateException;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.amparo.backend.exception.ApiError.EMAIL_JA_EXISTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final UserService userService;

    private final PatientRepository repository;


    @Override
    @Transactional
    public Patient saveNewPatient(Patient patientRegistrationData) {
        if (userService.alreadyExistsEmail(patientRegistrationData.getEmail())) {
            throw new ApiErrorException(EMAIL_JA_EXISTE.getMessage(), BAD_REQUEST);
        }

        return patientRegistrationData;
    }

    @Override
    public Optional<PatientResponse> findPatientByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public Optional<PatientResponse> editPatient(PatientToUpdateRequest patientToUpdateRequest) {
        if (repository.findByCpf(patientToUpdateRequest.cpf()).isEmpty()) {
            throw new ApiErrorException("Paciente não encontrado", BAD_REQUEST);
        } else {
            userService.updateUser(patientToUpdateRequest.toPatient());
            return repository.updatePatient(patientToUpdateRequest.toPatient());
        }
    }
}
