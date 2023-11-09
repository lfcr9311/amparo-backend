package br.com.amparo.backend.dto.information;

public record InformationResponse(
        String title,
        String link,
        String image,
        String description,
        String createdAt
) {
}
