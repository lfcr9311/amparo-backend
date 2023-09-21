package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.DoctorResponse;
import br.com.amparo.backend.dto.DoctorToUpdateRequest;


import java.util.Optional;

public interface DoctorService {

    Optional<DoctorResponse> findDoctorById(String id);
    Optional<DoctorResponse> editDoctor(DoctorToUpdateRequest doctorToUpdateRequest, String id);
}
