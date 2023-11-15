package br.com.amparo.backend.dto.information;

import java.util.Date;

public record InformationFindResponse(
        String title,
        String link,
        String image,
        String description,
        Date createdAt,
        String name,
        String crm,
        String uf
) {
}