package br.com.amparo.backend.dto.dosage;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;

public record DosageResponse(
        String id,
        String id_patient,
        String id_medicine,
        String quantity,
        LocalDateTime initial_hour,
        int frequency,
        LocalDateTime final_date,
        String medicine_name
) {}
