package br.com.amparo.backend.dto.doctor;

import br.com.amparo.backend.domain.entity.Doctor;

import java.util.UUID;
public record DoctorResponse(String id, String email, String name, String cellphone,
                              String profilePicture, boolean isAnonymous,
                              String crm, String uf){
    public DoctorResponse mapToCreateDoctorResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getEmail(),
                doctor.getName(),
                doctor.getCellphone(),
                doctor.getProfilePicture(),
                doctor.isAnonymous(),
                doctor.getCrm(),
                doctor.getUf()
        );
    }
}
