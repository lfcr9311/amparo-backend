package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;

import java.util.List;

public interface InformationService {
    InformationResponse create(Information information);

    List<InformationResponse> findAll();


}
