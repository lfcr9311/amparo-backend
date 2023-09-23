package br.com.amparo.backend.exception;

import java.time.LocalDateTime;

public class ExamCreationException extends CreationException{
    String description;
    LocalDateTime examDate;
    Boolean isDone;
    public ExamCreationException(String id, String description, LocalDateTime examDate, Boolean isDone) {
        super(id);
        this.description = description;
        this.examDate = examDate;
        this.isDone = isDone;
    }
}
