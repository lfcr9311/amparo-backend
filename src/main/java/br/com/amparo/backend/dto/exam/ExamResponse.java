package br.com.amparo.backend.dto.exam;

import java.time.LocalDateTime;

public record ExamResponse (String id, String description, LocalDateTime examDate,
                            boolean isDone, String id_patient, String file, String image) {
}
