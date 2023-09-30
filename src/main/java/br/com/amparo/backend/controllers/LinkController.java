package br.com.amparo.backend.controllers;


import br.com.amparo.backend.service.LinkService;
import br.com.amparo.backend.service.security.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Tag(name = "8. link controller")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping()
    public ResponseEntity<String> linkDoctorToPatient(@RequestParam("id") String patientId) {

            return linkService.linkDoctorToPatient(SecurityUtils.getApiUser().getId(), patientId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
