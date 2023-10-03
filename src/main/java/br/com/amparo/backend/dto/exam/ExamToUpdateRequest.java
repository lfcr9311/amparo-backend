package br.com.amparo.backend.dto.exam;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ExamToUpdateRequest(String description, @NotNull LocalDateTime examDate,@NotNull Boolean isDone, String file, String image) {
}