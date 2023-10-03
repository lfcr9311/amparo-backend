package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.service.ExamService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/patient/{id}/exam")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "5. PatientExam")
public class PatientExamController {
    private final ExamService examService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<?> addExam(@RequestBody CreateExamRequest exam, @PathVariable String id){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return examService.addExam(exam, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/done/list")
    public ResponseEntity<?> listDoneExams(@PathVariable String id, @RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(examService.listDoneExams(id, pageNumber, pageSize));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/pending/list")
    public ResponseEntity<?> listPendingExams(@PathVariable String id, @RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(examService.listPendingExams(id, pageNumber, pageSize));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{examId}")
    public ResponseEntity<?> findById(@PathVariable("examId") String examId, @PathVariable("id") String id) {
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return examService.findExamById(examId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/{examId}")
    public ResponseEntity<?> editExam(@RequestBody ExamToUpdateRequest exam, @PathVariable("examId") String examId){
        if (!Objects.equals(SecurityUtils.getApiUser().getId(), examService.findExamById(examId).get().id_patient())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return examService.editExam(exam,examId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
