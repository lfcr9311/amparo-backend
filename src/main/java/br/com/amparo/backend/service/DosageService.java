package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;

import java.util.List;
import java.util.Optional;

public interface DosageService {
    Optional<DosageResponse> create(int medicineId, AddDosageRequest request);

    Optional<DosageResponse> update(String dosageId, EditDosageRequest request);

    Optional<DosageResponse> findById(String dosageId);

    DosageResponse delete(String dosageId);

    List<DosageResponse> findAll(int pageNumber, int pageSize);
}
