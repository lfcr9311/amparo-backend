package br.com.amparo.backend.service;


import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.PatientResponse;
import br.com.amparo.backend.dto.PatientToUpdateRequest;

import java.util.Optional;

public interface PatientService {
    Optional<PatientResponse> findPatientByCpf(String cpf);

    Optional<PatientResponse> findPatientById(String id);
    Optional<PatientResponse> editPatient(PatientToUpdateRequest patientToUpdateRequest, String id);
}
