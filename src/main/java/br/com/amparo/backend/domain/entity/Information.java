package br.com.amparo.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class Information {
    private String title;
    private String link;
    private String image;
    private String description;
    private Date createdAt;

    @Builder
    public Information(String title, String link, String image, String description) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.description = description;
        this.createdAt = new Date();
    }
}
