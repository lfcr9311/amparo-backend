package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;
import br.com.amparo.backend.repository.DoctorRepository;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final UserService userService;

    private final DoctorRepository repository;

    @Override
    public Optional<DoctorResponse> findDoctorById(String id) {
        return repository.findDoctorById(id);
    }

    @Override
    public Optional<DoctorResponse> editDoctor(DoctorToUpdateRequest doctorToUpdateRequest, String id) {
        if (repository.findDoctorById(id).isEmpty()) {
            return Optional.empty();
        } else {
            userService.updateUser(doctorToUpdateRequest.toDoctor(id));
            return repository.updateDoctor(doctorToUpdateRequest.toDoctor(id));
        }
    }

    @Override
    public List<DoctorResponse> findAll(List<String> doctorIds) {
        return repository.findAll(doctorIds);
    }
}