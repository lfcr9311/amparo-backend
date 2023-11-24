package br.com.amparo.backend.dto.information;

import java.time.LocalDate;

public record InformationFindResponse(
        String id,
        String title,
        String link,
        String image,
        String description,
        LocalDate createdAt,
        String name,
        String doctorId,
        String crm,
        String uf
) {
}