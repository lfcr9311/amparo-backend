package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class MedicineOperationException extends RuntimeException{

    private String id;
    private String name;
    private String leaflet;

    public MedicineOperationException(String id, String name, String leaflet, Exception e) {
        super(e);
        this.id = id;
        this.name = name;
        this.leaflet = leaflet;
    }
}
