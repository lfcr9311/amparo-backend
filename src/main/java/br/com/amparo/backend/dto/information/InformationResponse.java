package br.com.amparo.backend.dto.information;

import java.time.LocalDate;

public record InformationResponse(
        String id,
        String title,
        String link,
        String image,
        String doctorId,
        String description,
        LocalDate createdAt
) {
}
