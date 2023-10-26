package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineIncompatibilityRequest;
import br.com.amparo.backend.service.MedicineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "6. Medicine")
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return medicineService.findMedicineById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return medicineService.findMedicineByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findAllMedicines(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(medicineService.findAllMedicines(pageNumber, pageSize));
    }

    @GetMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findAllIncompatibility(@PathVariable int id) {
        List<MedicineIncResponse> incompatibilities = medicineService.findAllIncompatibility(id);
        if (incompatibilities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(incompatibilities);
        }
    }

    @PostMapping("/incompatibility/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<?> findIncompatibility(@PathVariable int id,
                                                 @RequestBody MedicineIncompatibilityRequest request) {
        if (request.medicines().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MedicineIncResponse> incompatibilities = medicineService.findIncompatibility(id, request.medicines());
        if (incompatibilities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(incompatibilities);
        }
    }
}