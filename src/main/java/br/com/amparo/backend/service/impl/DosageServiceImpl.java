package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import br.com.amparo.backend.exception.NotFoundException;
import br.com.amparo.backend.repository.DosageRepository;
import br.com.amparo.backend.service.DosageService;
import br.com.amparo.backend.service.PatientService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DosageServiceImpl implements DosageService {
    private final DosageRepository repository;
    private final PatientService patientService;
    @Override
    public Optional<DosageResponse> create(int medicineId, AddDosageRequest request) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new NotFoundException("Patient");
        } else return repository.addDosage(patientId, medicineId, request);
    }

    @Override
    public Optional<DosageResponse> findById(String dosageId) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new NotFoundException("Patient");
        } else {
            Optional<DosageResponse> dosage = repository.getDosage(dosageId, patientId);
            if (dosage.isEmpty()) {
                throw new NotFoundException("Dosage");
            } else return dosage;
        }
    }

    @Override
    public DosageResponse delete(String dosageId) {
        Optional<DosageResponse> dosage = findById(dosageId);
        if (dosage.isEmpty()){
            throw new NotFoundException("Dosage");
        } else {
            String patientId = SecurityUtils.getApiUser().getId();
            if (patientService.findPatientById(patientId).isEmpty()) {
                throw new NotFoundException("Patient");
            } else if (repository.deleteDosage(dosage.get()) == 1) {
                return dosage.get();
            } else {
                throw new NotFoundException("Dosage");
            }
        }
    }

    @Override
    public List<DosageResponse> findAll(int pageNumber, int pageSize) {
        String patientId = SecurityUtils.getApiUser().getId();
        if(patientService.findPatientById(patientId).isEmpty()){
            throw new NotFoundException("Patient");
        } else return repository.listDosage(patientId, pageNumber, pageSize);
    }

    @Override
    public Optional<DosageResponse> update(String dosageId, EditDosageRequest request) {
        String patientId = SecurityUtils.getApiUser().getId();
        if (patientService.findPatientById(patientId).isEmpty()) {
            throw new NotFoundException("Patient");
        } else {
            Optional<DosageResponse> dosage = repository.editDosage(dosageId, request, patientId);
            if (dosage.isEmpty()) {
                throw new NotFoundException("Dosage");
            } else return dosage;
        }
    }
}
