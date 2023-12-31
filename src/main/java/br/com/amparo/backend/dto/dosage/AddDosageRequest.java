package br.com.amparo.backend.dto.dosage;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;

public record AddDosageRequest(
        @NotNull String quantity,
        @NotNull LocalDateTime initialHour,
        @NotNull String frequency,
        LocalDateTime finalDate
) {}
