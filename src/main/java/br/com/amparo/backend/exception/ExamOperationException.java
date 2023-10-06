package br.com.amparo.backend.exception;

public class ExamOperationException extends RuntimeException {
    String description;
    String patientId;
    String LocalDateTime;

    public ExamOperationException(String id, String description, String patientId, String LocalDateTime) {
        super(id);
        this.description = description;
        this.patientId = patientId;
        this.LocalDateTime = LocalDateTime;
    }
}
