package br.com.amparo.backend.service;

import java.util.Optional;

public interface LinkService {
    Boolean linkDoctorToPatient(String doctorId, String patientId);
}
