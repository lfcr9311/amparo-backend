package br.com.amparo.backend.dto.information;

import java.time.LocalDate;

public record InformationResponse(
        String title,
        String link,
        String image,
        String description,
        LocalDate createdAt
) {
}
