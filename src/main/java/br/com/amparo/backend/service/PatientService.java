package br.com.amparo.backend.service;


import br.com.amparo.backend.domain.record.PatientRegistrationData;


public interface PatientService {
    PatientRegistrationData saveNewPatient(PatientRegistrationData patientRegistrationData);
}
