package br.com.amparo.backend.dto.exam;

import java.time.LocalDateTime;

public record ExamToUpdateRequest(String description, LocalDateTime examDate, Boolean isDone) {
    public boolean isValid() {
        return this.examDate != null && this.isDone != null;
    }
}
