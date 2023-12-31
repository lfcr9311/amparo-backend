package br.com.amparo.backend.dto.patient;

public record PatientResponse(String id, String email, String name, String cellphone,
                              String profilePicture, boolean isAnonymous, String cpf,
                              String birthDate, String numSus) {
}
