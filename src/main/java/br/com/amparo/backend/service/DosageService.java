package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;

import java.util.List;
import java.util.Optional;

public interface DosageService {
    Optional<DosageResponse> addDosage(int medicineId, AddDosageRequest request);

    Optional<DosageResponse> editDosage(String dosageId, EditDosageRequest request);

    Optional<DosageResponse> getDosage(String dosageId);

    Optional<DosageResponse> deleteDosage(DosageResponse dosage);

    List<DosageResponse> listDosage(int pageNumber, int pageSize);
}
