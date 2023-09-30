package br.com.amparo.backend.dto.medicine;

import br.com.amparo.backend.domain.entity.Medicine;


public record MedicineResponse (String id, String name, String leaflet) {
    public MedicineResponse mapToCreateDoctorResponse(Medicine medicine) {
        return new MedicineResponse(
            medicine.getId(),
            medicine.getName(),
            medicine.getLeaflet()
        );
    }
}
