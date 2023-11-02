package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.service.DoctorPatientService;
import br.com.amparo.backend.service.ExamService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class DoctorPatientServiceImpl implements DoctorPatientService {
    private final ExamService examService;

    @Override
    public List<ExamResponse> findDoneExamsToPatient(String patientId, int pageNumber, int pageSize) {
        return examService.listDoneExams(patientId, pageNumber, pageSize);
    }

    @Override
    public List<ExamResponse> findPendingExamsToPatient(String patientId, int pageNumber, int pageSize) {
        return examService.listPendingExams(patientId, pageNumber, pageSize);
    }
}
