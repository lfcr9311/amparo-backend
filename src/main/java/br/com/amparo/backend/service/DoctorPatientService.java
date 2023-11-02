package br.com.amparo.backend.service;

import br.com.amparo.backend.dto.exam.ExamResponse;
import java.util.List;

public interface DoctorPatientService {
    List<ExamResponse> findDoneExamsToPatient(String patientId, int pageNumber, int pageSize);

    List<ExamResponse> findPendingExamsToPatient(String patientId, int pageNumber, int pageSize);
}
