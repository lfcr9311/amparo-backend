package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.PatientResponse;
import br.com.amparo.backend.dto.PatientToUpdateRequest;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final UserService userService;

    private final PatientRepository repository;

    @Override
    public Optional<PatientResponse> findPatientByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public Optional<PatientResponse> editPatient(PatientToUpdateRequest patientToUpdateRequest, String id) {
        if (repository.findById(id).isEmpty()) {
            return Optional.empty();
        } else {
            userService.updateUser(patientToUpdateRequest.toPatient(id));
            return repository.updatePatient(patientToUpdateRequest.toPatient(id));
        }
    }
}
