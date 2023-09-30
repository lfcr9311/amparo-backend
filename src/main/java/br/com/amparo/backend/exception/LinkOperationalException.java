package br.com.amparo.backend.exception;

public class LinkOperationalException extends RuntimeException{
    private String doctorId;
    private String patientId;
    public LinkOperationalException(String doctorId, String patientId, Exception e) {
        super(e);
        this.doctorId = doctorId;
        this.patientId = patientId;
    }
}
