package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineResponse;
import br.com.amparo.backend.repository.MedicineRepository;
import br.com.amparo.backend.service.MedicineService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

    @Override
    public Optional<MedicineResponse> findMedicineById(int id) {
        return repository.findMedicineById(id);
    }

    @Override
    public List<MedicineResponse> findMedicineByName(String name) {
        return repository.findMedicineByName(name);
    }

    public List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize) {
        return repository.findAllMedicines(pageNumber, pageSize);
    }

    public List<MedicineIncResponse> findAllIncompatibility(int id) {
        return repository.findAllIncompatibility(id);
    }

    public List<MedicineIncResponse> findIncompatibility(int id, List<Integer> medicineIds) {
        return repository.findIncompatibility(id, medicineIds);
    }
}
