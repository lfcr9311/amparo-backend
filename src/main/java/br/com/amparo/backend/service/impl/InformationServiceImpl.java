package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.repository.InformationRepository;
import br.com.amparo.backend.service.InformationService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public InformationResponse create(Information information, String id) throws IllegalAccessException {
        if (!Objects.equals(id, SecurityUtils.getApiUser().getId())) {
            throw new IllegalAccessException();
        }
        return informationRepository.create(information, id);
    }

    @Override
    public List<InformationResponse> findAll() {
        return informationRepository.findAll();
    }

    @Override
    public List<InformationResponse> findByTitle(String title) {
        return informationRepository.findByTitle(title);
    }

    @Override
    public List<InformationResponse> orderByDate() {
        return informationRepository.orderByDate();
    }
}
