package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InformationService {
    InformationResponse create(Information information, Doctor id);

    List<InformationResponse> findAll();

    List<InformationResponse> findByTitle(String title);


}
