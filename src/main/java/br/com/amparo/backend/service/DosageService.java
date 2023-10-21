package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;

import java.util.Optional;

public interface DosageService {
    Optional<DosageResponse> addDosage(String medicineId, AddDosageRequest request);

    Optional<DosageResponse> editDosage(String dosageId, EditDosageRequest request) throws IllegalAccessException;

    Optional<DosageResponse> getDosage(String dosageId) throws IllegalAccessException;

    Optional<DosageResponse> deleteDosage(DosageResponse dosage) throws IllegalAccessException;
}
