package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
import br.com.amparo.backend.exception.NotFoundException;
import br.com.amparo.backend.repository.DoctorRepository;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final UserService userService;

    private final DoctorRepository repository;

    @Override
        public DoctorResponse findDoctorById(String id) {
        Optional<DoctorResponse> doctor = repository.findDoctorById(id);
        if (doctor.isEmpty()) {
            throw new NotFoundException("Doctor");
        } else {
            return doctor.get();
        }
    }

    @Override
    public DoctorResponse editDoctor(DoctorToUpdateRequest doctorToUpdateRequest, String id) {
        if (repository.findDoctorById(id).isEmpty()) {
            throw new NotFoundException("Doctor");
        } else {
            userService.updateUser(doctorToUpdateRequest.toDoctor(id));
            Optional<DoctorResponse> doctor = repository.updateDoctor(doctorToUpdateRequest.toDoctor(id));
            if (doctor.isEmpty()){
                throw new RuntimeException("Fatal Error: Doctor might not be updated");
            } else {
                return doctor.get();
            }
        }
    }

    @Override
    public List<DoctorResponse> findAll(List<UUID> doctorIds) {
        return repository.findAll(doctorIds);
    }
}