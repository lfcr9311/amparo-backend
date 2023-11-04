package br.com.amparo.backend.controllers;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.service.InformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/information")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "10. Information")

public class InformationController {

    @Autowired
    private final InformationService informationService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<InformationResponse> create(@RequestBody Information information){
        return new ResponseEntity<>(informationService.create(information), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<List<InformationResponse>> findAll() {
        return ResponseEntity.ok(informationService.findAll());

    }

    @GetMapping("/{title}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
    public ResponseEntity<List<InformationResponse>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(informationService.findByTitle(title));
    }

}
