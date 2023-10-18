package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import br.com.amparo.backend.repository.DosageRepository;
import br.com.amparo.backend.service.DosageService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DosageServiceImpl implements DosageService {
    private final DosageRepository repository;
    @Override
    public Optional<DosageResponse> addDosage(String patientId, String medicineId, AddDosageRequest request) {
        return repository.addDosage(patientId, medicineId, request);
    }

    @Override
    public Optional<DosageResponse> getDosage(String dosageId) {
        return repository.getDosage(dosageId);
    }

    @Override
    public Optional<DosageResponse> deleteDosage(DosageResponse dosage) {
        return repository.deleteDosage(dosage);
    }

    @Override
    public Optional<DosageResponse> editDosage(String patientId, String dosageId, EditDosageRequest request) {
        return repository.editDosage(dosageId, request);
    }
}
