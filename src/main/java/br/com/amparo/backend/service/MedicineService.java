package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.medicine.MedicineResponse;

import java.util.Optional;

public interface MedicineService {

    Optional<MedicineResponse> findMedicineById(String id);
    Optional<MedicineResponse> findMedicineByName(String name);


}
