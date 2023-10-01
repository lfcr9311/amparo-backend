package br.com.amparo.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Medicine {

    private String id;
    private String name;
    private String leaflet;

    @Builder
    public Medicine(String id, String name, String leaflet) {
        this.id = id;
        this.name = name;
        this.leaflet = leaflet;
    }
}
