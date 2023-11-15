package br.com.amparo.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
public class Information {
    private String title;
    private String link;
    private String image;
    private String description;
    private LocalDate createdAt;

    @Builder
    public Information(String title, String link, String image, String description) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.description = description;
        this.createdAt = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }
}
