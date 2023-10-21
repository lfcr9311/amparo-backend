package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.repository.ExamRepository;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.service.ExamService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository repository;

    private final PatientRepository patientRepository;
    @Override
    public Optional<ExamResponse> addExam( CreateExamRequest request, String examId) {
       String patientId = SecurityUtils.getApiUser().getId();
        if (patientRepository.findById(patientId).isEmpty()) {
            throw new RuntimeException(patientId);
        }else {
            return repository.addExam(request, examId);
        }
    }

    @Override
    public List<ExamResponse> listDoneExams(String id, int page, int size) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Patient not found");
        }
        return repository.listDoneExams(id, page, size);
    }

    @Override
    public List<ExamResponse> listPendingExams(String id, int page, int size) {
        if (patientRepository.findById(id).isEmpty()) {
            // Deixa assim por enquanto, quando tratarmos corretamente as exceptions, vamos mudar
            throw new RuntimeException("Patient not found");
        }
        return repository.listPendingExams(id, page, size);
    }

    @Override
    public Optional<ExamResponse> findExamById(String id) {
        return repository.findExamById(id);
    }

    @Override
    public Optional<ExamResponse> editExam(ExamToUpdateRequest examRequest, String id) {
        return repository.editExam(examRequest, id);
    }
}
