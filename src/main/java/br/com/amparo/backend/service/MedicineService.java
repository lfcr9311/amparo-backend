package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.medicine.MedicineResponse;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    Optional<MedicineResponse> findMedicineById(int id);
    Optional<MedicineResponse> findMedicineByName(String name);
    Optional<MedicineResponse> findIncompatibility(int id);
    List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize);

}
