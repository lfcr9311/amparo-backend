package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface ExamService {
    Optional<ExamResponse> addExam(CreateExamRequest examRequest, String id);

    List<ExamResponse> listDoneExams(String id, int page, int size, ApiUser user);

    List<ExamResponse> listPendingExams(String id, int page, int size, ApiUser user);

    List<ExamResponse> listAllExams(String id, int page, int size, ApiUser user);

    Optional<ExamResponse> findExamById(String id);

    Optional<ExamResponse> editExam(ExamToUpdateRequest examRequest, String id);
}
