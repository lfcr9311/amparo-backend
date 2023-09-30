package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
import br.com.amparo.backend.dto.medicine.MedicineResponse;
import br.com.amparo.backend.repository.DoctorRepository;
import br.com.amparo.backend.repository.MedicineRepository;
import br.com.amparo.backend.service.MedicineService;
import br.com.amparo.backend.service.UserService;

import java.util.Optional;

public class MedicineServiceImpl extends MedicineService {
    private final MedicineService medicineService;

    private final MedicineRepository repository;

    @Override
    public Optional<MedicineResponse> findMedicineById(String id) {
        return repository.findMedicineById(id);
    }

    @Override
    public
}
