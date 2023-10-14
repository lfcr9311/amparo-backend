package br.com.amparo.backend.exception;

import lombok.Getter;

@Getter
public class MedicineOperationException extends RuntimeException{

    private int id;
    private String name;
    private String leaflet;

    public MedicineOperationException(Integer id, String name, String leaflet, Exception e) {
        super(e);
        this.id = id;
        this.name = name;
        this.leaflet = leaflet;
    }
}
