package br.com.amparo.backend.web.controller;

import br.com.amparo.backend.web.entity.Patient;
import br.com.amparo.backend.web.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("patients")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public void create(@RequestBody Patient patient) {
        patientRepository.save(patient);
    }
}
