package br.com.amparo.backend.service;


import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.PatientResponse;
import br.com.amparo.backend.dto.PatientToUpdateRequest;

import java.util.Optional;

public interface PatientService {
    Patient saveNewPatient(Patient patient);

    Optional<PatientResponse> findPatientByCpf(String cpf);

    Optional<PatientResponse> editPatient(PatientToUpdateRequest patientToUpdateRequest);
    Optional<PatientResponse> findPatientById(String id);
}
