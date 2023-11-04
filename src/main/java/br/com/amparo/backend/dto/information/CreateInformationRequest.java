package br.com.amparo.backend.dto.information;

import jakarta.validation.constraints.NotNull;

public record CreateInformationRequest(
        @NotNull String title,
        String link,
        String image,
        String description
        ) {
}
