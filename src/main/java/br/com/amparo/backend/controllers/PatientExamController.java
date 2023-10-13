package br.com.amparo.backend.controllers;

import br.com.amparo.backend.controllers.dto.ErrorMessage;
import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.service.ExamService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
                    @ApiResponse(responseCode = "201", description = "Added Exam",
                            content = @Content(schema = @Schema(implementation = ExamResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Don't have permission to access / on this server",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Exam not Added",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<?> addExam(
            @RequestBody CreateExamRequest exam,
            @PathVariable
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "1"
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
                    @ApiResponse(responseCode = "200", description = "Exams found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamResponse.class)))
                    ),
                    @ApiResponse(responseCode = "403", description = "Don't have permission to access / on this server",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/done/list")
    public ResponseEntity<?> listDoneExams(
            @PathVariable
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "1"
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
                    @ApiResponse(responseCode = "200", description = "Exams found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamResponse.class)))
                    ),
                    @ApiResponse(responseCode = "403", description = "Don't have permission to access / on this server",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/pending/list")
    public ResponseEntity<?> listPendingExams(
            @PathVariable
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "1"
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
                    @ApiResponse(responseCode = "200", description = "Exam found",
                            content = @Content(schema = @Schema(implementation = ExamResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Don't have permission to access / on this server",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Exam not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{examId}")
    public ResponseEntity<?> findById(
            @PathVariable("examId")
            @Parameter(
                    name = "examId",
                    description = "Exam Id",
                    example = "1"
            ) String examId,
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "a7f6b9c0a8f0d2c4f1e9b5c8f3c6a0e2a3d9b4d1a7d3e6c5a9f8b7d0a8f1e2c4"
            )
            @PathVariable("id") String id
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
                    @ApiResponse(responseCode = "200", description = "Altered exam",
                            content = @Content(schema = @Schema(implementation = ExamResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Don't have permission to access / on this server",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Exam not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/{examId}")
    public ResponseEntity<?> editExam(
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
