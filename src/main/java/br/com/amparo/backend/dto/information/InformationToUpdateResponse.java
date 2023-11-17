package br.com.amparo.backend.dto.information;

public record InformationToUpdateResponse(
        String title,
        String link,
        String image,
        String description
) {
}
