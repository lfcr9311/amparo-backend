package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;

import java.util.Optional;

public interface DosageService {
    Optional<DosageResponse> addDosage(String patientId, String medicineId, AddDosageRequest request);

    Optional<DosageResponse> editDosage(String patientId, String dosageId, EditDosageRequest request);

    Optional<DosageResponse> getDosage(String dosageId);

    Optional<DosageResponse> deleteDosage(DosageResponse dosage);
}
