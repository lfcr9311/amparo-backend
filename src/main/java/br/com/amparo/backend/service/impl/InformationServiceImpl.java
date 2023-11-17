package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationFindResponse;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.dto.information.InformationToUpdateResponse;
import br.com.amparo.backend.repository.InformationRepository;
import br.com.amparo.backend.service.InformationService;
import br.com.amparo.backend.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public InformationResponse create(Information information, String id) throws IllegalAccessException {
        return informationRepository.create(information, id);
    }

    @Override
    public List<InformationFindResponse> findAll() {
        return informationRepository.findAll();
    }

    @Override
    public List<InformationFindResponse> findByTitle(String title) {
        return informationRepository.findByTitle(title);
    }

    @Override
    public Optional<InformationResponse> findInformationById(String id) {
        return informationRepository.findInformationById(id);
    }

    @Override
    public Optional<InformationResponse> updateInformation(String id, InformationToUpdateResponse informationRequest) {
        return informationRepository.updateInformation(id, informationRequest, SecurityUtils.getApiUser().getId());
    }

    @Override
    public List<InformationFindResponse> findAllDoctor() {
        return informationRepository.findAllDoctor(SecurityUtils.getApiUser().getId());
    }
}
