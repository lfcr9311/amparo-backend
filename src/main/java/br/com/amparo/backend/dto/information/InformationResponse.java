package br.com.amparo.backend.dto.information;

import java.util.Date;

public record InformationResponse(
        String title,
        String link,
        String image,
        String description,
        Date createdAt
) {
}
