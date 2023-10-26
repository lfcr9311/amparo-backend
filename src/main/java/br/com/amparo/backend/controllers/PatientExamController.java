package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.service.ExamService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/patient/{id}/exam")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "5. PatientExam")
public class PatientExamController {
    private final ExamService examService;

    @Operation(operationId = "addExam", description = "Add a new exam",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added Exam"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A patient with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<ExamResponse> addExam(
            @RequestBody CreateExamRequest exam,
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String id
    ){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return examService.addExam(exam, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "listDoneExams", description = "List done exams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Done exams found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/done/list")
    public ResponseEntity<List<ExamResponse>> listDoneExams(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String id,
            @Parameter(
                    name = "pageNumber",
                    description = "Current page number",
                    required = true
            )
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(
                    name = "pageSize",
                    description = "Number of items per page",
                    required = true
            )
            @RequestParam(defaultValue = "10") int pageSize
    ){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(examService.listDoneExams(id, pageNumber, pageSize));
    }

    @Operation(operationId = "listPendingExams", description = "List pending exams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pending exams found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/pending/list")
    public ResponseEntity<List<ExamResponse>> listPendingExams(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String id,
            @Parameter(
                    name = "pageNumber",
                    description = "Current page number",
                    required = true
            )
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(
                    name = "pageSize",
                    description = "Number of items per page",
                    required = true
            )
            @RequestParam(defaultValue = "10") int pageSize
    ){
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(examService.listPendingExams(id, pageNumber, pageSize));
    }

    @Operation(operationId = "findExamById", description = "Find a exam by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exam found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A exam with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{examId}")
    public ResponseEntity<ExamResponse> findById(
            @PathVariable("examId")
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "77a9c8jd-0c00-4fb2-ca68-0ed898d2a3c7"
            ) String examId,
            @PathVariable("id")
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String id
    ) {
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return examService.findExamById(examId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(operationId = "editExam", description = "Edit a exam",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Altered exam"),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(responseCode = "404", description = "A exam with the specified ID was not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponse> editExam(
            @RequestBody ExamToUpdateRequest exam,
            @PathVariable("examId")
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "1"
            ) String examId
    ){
        if (!Objects.equals(SecurityUtils.getApiUser().getId(), examService.findExamById(examId).get().id_patient())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return examService.editExam(exam,examId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
