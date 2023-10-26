package br.com.amparo.backend.dto.medicine;

import java.util.List;

public record MedicineIncompatibilityRequest(List<Integer> medicines) {
}
