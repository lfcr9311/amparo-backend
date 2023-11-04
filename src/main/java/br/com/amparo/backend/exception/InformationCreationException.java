package br.com.amparo.backend.exception;

public class InformationCreationException extends RuntimeException{
    String title;
    String link;
    String image;
    String description;
    public InformationCreationException(Exception e, String title, String link, String image, String description) {
        super(e);
        this.title = title;
        this.link = link;
        this.image = image;
        this.description = description;
    }
}
