package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineResponse;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    Optional<MedicineResponse> findMedicineById(int id);
    List<MedicineResponse> findMedicineByName(String name);
    List<MedicineIncResponse> findAllIncompatibility(int id);
    List<MedicineResponse> findAllMedicines();
    List<MedicineIncResponse> findIncompatibility(int id, List<Integer> medicineIds);

}
