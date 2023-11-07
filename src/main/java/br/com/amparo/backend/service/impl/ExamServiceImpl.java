package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.exception.NotFoundException;
import br.com.amparo.backend.repository.ExamRepository;
import br.com.amparo.backend.repository.PatientRepository;
import br.com.amparo.backend.service.ExamService;
import br.com.amparo.backend.service.LinkService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository repository;
    private final PatientRepository patientRepository;
    private final LinkService linkService;

    @Override
    public Optional<ExamResponse> addExam(CreateExamRequest exam, String id) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient");
        }
        return repository.addExam(exam, id);
    }

    @Override
    public List<ExamResponse> listDoneExams(String id, int page, int size, ApiUser apiUser) {
        if (apiUser.isDoctor() && !linkService.checkConnection(apiUser.getId(), id)) {
            throw new NotFoundException("Connection");
        }

        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient");
        }

        return repository.listDoneExams(id, page, size);
    }

    @Override
    public List<ExamResponse> listPendingExams(String id, int page, int size, ApiUser apiUser) {
        if (apiUser.isDoctor() && !linkService.checkConnection(apiUser.getId(), id)) {
            throw new NotFoundException("Connection");
        }

        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient");
        }

        return repository.listPendingExams(id, page, size);
    }

    @Override
    public List<ExamResponse> listAllExams(String id, int page, int size, ApiUser apiUser) {
        if (apiUser.isDoctor() && !linkService.checkConnection(apiUser.getId(), id)) {
            throw new NotFoundException("Connection");
        }

        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient");
        }

        return repository.listAllExams(id, page, size);
    }

    @Override
    public Optional<ExamResponse> findExamById(String id) {
        if (patientRepository.findById(SecurityUtils.getApiUser().getId()).isEmpty()) {
            throw new NotFoundException("Patient");
        }
        return repository.findExamById(id, SecurityUtils.getApiUser().getId());
    }

    @Override
    public Optional<ExamResponse> editExam(ExamToUpdateRequest examRequest, String id) {
        if (patientRepository.findById(SecurityUtils.getApiUser().getId()).isEmpty()) {
            throw new NotFoundException("Patient");
        }
        return repository.editExam(examRequest, id, SecurityUtils.getApiUser().getId());
    }
}
