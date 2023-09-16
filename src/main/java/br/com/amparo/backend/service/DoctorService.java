package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Doctor;

public interface DoctorService {
    Doctor saveNewDoctor(Doctor doctor);

    boolean alreadyExistsCrm(String crm);
}
