package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.dto.patient.PatientToUpdateRequest;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<PatientResponse> findPatientById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<PatientResponse> findAll(List<UUID> patientIds) {
        return repository.findAll(patientIds);
    }
}
