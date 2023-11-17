package br.com.amparo.backend.dto.dosage;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EditDosageRequest(
        @NotNull int medicineId,
        @NotNull String quantity,
        @NotNull String frequency,
        LocalDateTime finalDate,
        LocalDateTime lastConsumedDate
) {}
