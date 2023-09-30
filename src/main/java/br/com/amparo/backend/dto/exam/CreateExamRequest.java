package br.com.amparo.backend.dto.exam;

import java.time.LocalDateTime;

public record CreateExamRequest (String description, LocalDateTime examDate, Boolean isDone, String file, String image){
    public boolean isValid() {
        return this.examDate != null && this.isDone != null;
    }
}

