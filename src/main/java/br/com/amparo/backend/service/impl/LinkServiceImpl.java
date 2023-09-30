package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.repository.DoctorRepository;
import br.com.amparo.backend.repository.LinkRepository;
import br.com.amparo.backend.service.LinkService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;

    Boolean linkDoctorToPatient(String doctorId, String patientId){

    }
}