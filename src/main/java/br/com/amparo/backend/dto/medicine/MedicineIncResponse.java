package br.com.amparo.backend.dto.medicine;


public record MedicineIncResponse(int idMedicine, idMedicineInc, String incompatibility) {
    public MedicineIncResponse {
        this.idMedicine = idMedicine;
        this.idMedicineInc = idMedicineInc;
        this.incompatibility = incompatibility;
    }
}
