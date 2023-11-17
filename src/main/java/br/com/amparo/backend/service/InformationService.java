package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationFindResponse;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.dto.information.InformationToUpdateResponse;

import java.util.List;
import java.util.Optional;

public interface InformationService {
    InformationResponse create(Information information, String id) throws IllegalAccessException;

    List<InformationFindResponse> findAll();

    List<InformationFindResponse> findByTitle(String title);

    Optional<InformationResponse> findInformationById(String id);

    Optional<InformationResponse> updateInformation(String id, InformationToUpdateResponse informationRequest);

    List<InformationFindResponse> findAllDoctor();

}
