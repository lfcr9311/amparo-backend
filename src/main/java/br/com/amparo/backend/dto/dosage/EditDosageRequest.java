package br.com.amparo.backend.dto.dosage;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EditDosageRequest(
        @NotNull String medicine_id,
        @NotNull String quantity,
        @NotNull int frequency,
        @NotNull LocalDateTime final_date
) {}
