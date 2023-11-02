package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.patient.PatientResponse;
import java.util.List;

public interface LinkService {
    Boolean linkDoctorToPatient(String doctorId, String patientId);

    Boolean checkConnectionRequest(String doctorId, String patientId);

    Boolean requestDoctorToPatient(String doctorId, String patientId);

    Boolean checkConnection(String doctorId, String patientId);

    Boolean deleteLinkPatient(String doctor, String patientId);

    Boolean deleteLinkDoctor(String doctor, String patientId);

    List<DoctorResponse> getAllDoctorOfPatient(String patientId);

    List<PatientResponse> getAllPatientOfDoctor(String doctorId);
}
