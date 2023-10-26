package br.com.amparo.backend.dto.dosage;

import java.time.LocalDateTime;

public record DosageResponse(
        String id,
        String idPatient,
        String idMedicine,
        String quantity,
        LocalDateTime initialHour,
        int frequency,
        LocalDateTime finalDate,
        String medicineName
) {}
