package br.com.amparo.backend.dto.dosage;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;

public record AddDosageRequest(
        @NotNull String quantity,
        @Timestamp LocalDateTime initialHour,
        @NotNull int frequency,
        @Timestamp LocalDateTime finalDate
) {}
