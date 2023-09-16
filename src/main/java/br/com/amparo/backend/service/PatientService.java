package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Patient;

public interface PatientService {
    Patient saveNewPatient(Patient patient);
}
