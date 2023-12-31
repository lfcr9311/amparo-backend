package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.doctor.DoctorToUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorService {

    DoctorResponse findDoctorById(String id);
    DoctorResponse editDoctor(DoctorToUpdateRequest doctorToUpdateRequest, String id);
    List<DoctorResponse> findAll(List<UUID> doctorIds);
    Optional<DoctorResponse> findByCrm(String crm, String uf);
}
