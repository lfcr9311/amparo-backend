package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import br.com.amparo.backend.exception.PatientNotFoundException;
import br.com.amparo.backend.repository.DosageRepository;
import br.com.amparo.backend.service.DosageService;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DosageServiceImpl implements DosageService {
    private final DosageRepository repository;
    private final PatientService patientService;
    @Override
    public Optional<DosageResponse> addDosage(String medicineId, AddDosageRequest request) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new PatientNotFoundException(patientId);
        } else {
            return repository.addDosage(patientId, medicineId, request);
        }
    }
    @Override
    public Optional<DosageResponse> getDosage(String dosageId) throws IllegalAccessException {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new PatientNotFoundException(patientId);
        } else if (!patientId.equals(repository.getDosage(dosageId).get().idPatient())) {
            throw new IllegalAccessException(patientId);
        } else {
            return repository.getDosage(dosageId);
        }
    }

    @Override
    public Optional<DosageResponse> deleteDosage(DosageResponse dosage) throws IllegalAccessException {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new PatientNotFoundException(patientId);
        } else if (!patientId.equals(repository.getDosage(dosage.id()).get().idPatient())) {
            throw new IllegalAccessException(patientId);
        } else {
            return repository.deleteDosage(dosage);
        }
    }

    @Override
    public Optional<DosageResponse> editDosage(String dosageId, EditDosageRequest request) throws IllegalAccessException {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new PatientNotFoundException(patientId);
        } else if (!patientId.equals(repository.getDosage(dosageId).get().idPatient())) {
            throw new IllegalAccessException(patientId);
        } else {
            return repository.editDosage(dosageId, request);
        }
    }
}
