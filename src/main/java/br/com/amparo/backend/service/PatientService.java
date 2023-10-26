package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.dto.patient.PatientToUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Optional<PatientResponse> findPatientByCpf(String cpf);
    Optional<PatientResponse> findPatientById(String id);
    Optional<PatientResponse> editPatient(PatientToUpdateRequest patientToUpdateRequest, String id);
    List<PatientResponse> findAll(List<String> patientIds);
}
