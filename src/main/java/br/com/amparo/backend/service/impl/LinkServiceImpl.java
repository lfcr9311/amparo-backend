package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.repository.LinkRepository;
import br.com.amparo.backend.service.LinkService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;

    public Boolean linkDoctorToPatient(String doctorId, String patientId){
        return linkRepository.linkDoctorToPatient(doctorId, patientId);

    }

    @Override
    public Boolean checkConnection(String doctorId, String patientId) {
        return linkRepository.checkConnection(doctorId, patientId);
    }
}