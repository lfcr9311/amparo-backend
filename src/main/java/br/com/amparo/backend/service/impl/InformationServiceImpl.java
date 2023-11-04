package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.repository.InformationRepository;
import br.com.amparo.backend.service.InformationService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public InformationResponse create(Information information) {
        return informationRepository.create(information, SecurityUtils.getApiUser().getId());
    }

    @Override
    public List<InformationResponse> findAll() {
        return informationRepository.findAll(SecurityUtils.getApiUser().getId());
    }

}
