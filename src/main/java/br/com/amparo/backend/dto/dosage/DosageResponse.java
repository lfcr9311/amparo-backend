package br.com.amparo.backend.dto.dosage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DosageResponse(
        String id,
        String idPatient,
        String idMedicine,
        String quantity,
        LocalDateTime initialHour,
        String frequency,
        LocalDateTime finalDate,
        LocalDate lastConsumedDate,
        String medicineName
) {
}
