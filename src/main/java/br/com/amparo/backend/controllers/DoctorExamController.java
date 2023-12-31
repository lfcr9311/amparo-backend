package br.com.amparo.backend.controllers;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.dto.ErrorMessage;
import br.com.amparo.backend.dto.exam.ExamResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctor/exams")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "10. DoctorExam controller")
public class DoctorExamController {

    @Autowired
    private ExamService examService;

    @Operation(operationId = "findDoneExamsPatient", description = "Doctor access to a patient's done exams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Done exams found"),
                    @ApiResponse(responseCode = "404", description = "Patient or Connection not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/done/{patientId}")
    public ResponseEntity<List<ExamResponse>> findDoneExamsToPatient(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String patientId,
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
    ) {
        ApiUser user = SecurityUtils.getApiUser();
        return ResponseEntity.ok(examService.listDoneExams(patientId, pageNumber, pageSize, user));
    }

    @Operation(operationId = "findDoneExamsPatient", description = "Doctor access to a patient's done exams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Done exams found"),
                    @ApiResponse(responseCode = "404", description = "Patient or Connection not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/pending/{patientId}")
    public ResponseEntity<List<ExamResponse>> findPendingExamsToPatient(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String patientId,
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
    ) {
        ApiUser user = SecurityUtils.getApiUser();
        return ResponseEntity.ok(examService.listPendingExams(patientId, pageNumber, pageSize, user));
    }

    @Operation(operationId = "findAllExamsPatient", description = "Doctor access to a patient's exams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All exams found"),
                    @ApiResponse(responseCode = "404", description = "Patient or Connection not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/{patientId}")
    public ResponseEntity<List<ExamResponse>> findAllExamsToPatient(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "Patient Id",
                    example = "66e9b9bd-0c27-4fb2-ba78-0ed898d2a3b6"
            ) String patientId,
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
    ) {
        ApiUser user = SecurityUtils.getApiUser();
        return ResponseEntity.ok(examService.listAllExams(patientId, pageNumber, pageSize, user));
    }
}
