package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.repository.InformationRepository;
import br.com.amparo.backend.service.InformationService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public InformationResponse create(Information information, String doctorId) {
        return informationRepository.create(information, doctorId);
    }

    @Override
    public List<InformationResponse> findAll() {
        return informationRepository.findAll();
    }

    @Override
    public List<InformationResponse> findByTitle(String title) {
        return informationRepository.findByTitle(title);
    }


}
