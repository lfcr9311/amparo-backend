package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineResponse;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    Optional<MedicineResponse> findMedicineById(int id);
    Optional<MedicineResponse> findMedicineByName(String name);
    Optional<List<MedicineIncResponse>> findIncompatibility(int id);
    List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize);

}
