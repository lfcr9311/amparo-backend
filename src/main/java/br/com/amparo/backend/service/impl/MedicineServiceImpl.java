package br.com.amparo.backend.service.impl;

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
    public Optional<MedicineResponse> findMedicineById(String id) {
        return repository.findMedicineById(id);
    }

    @Override
    public Optional<MedicineResponse> findMedicineByName(String name) {
        return repository.findMedicineByName(name);
    }

    public List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize) {
        return repository.findAllMedicines(pageNumber, pageSize);
    }
}