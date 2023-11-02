package br.com.amparo.backend.exception;

import java.time.LocalDateTime;

public class ExamCreationException extends CreationException{
    String description;
    LocalDateTime examDate;
    Boolean isDone;
    public ExamCreationException(Exception e, String description, LocalDateTime examDate, Boolean isDone) {
        super(e);
        this.description = description;
        this.examDate = examDate;
        this.isDone = isDone;
    }
}
