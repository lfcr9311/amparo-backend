package br.com.amparo.backend.dto.information;

import java.time.LocalDate;

public record InformationFindResponse(
        String title,
        String link,
        String image,
        String description,
        LocalDate createdAt,
        String name,
        String crm,
        String uf
) {
}