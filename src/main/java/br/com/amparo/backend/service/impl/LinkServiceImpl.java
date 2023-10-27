package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.repository.LinkRepository;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.LinkService;
import br.com.amparo.backend.service.PatientService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public Boolean linkDoctorToPatient(String doctorId, String patientId) {
        return linkRepository.linkDoctorToPatient(doctorId, patientId);

    }

    @Override
    public Boolean checkConnectionRequest(String doctorId, String patientId) {
        return linkRepository.checkConnectionRequest(doctorId, patientId);
    }

    @Override
    public Boolean checkConnection(String doctorId, String patientId) {
        return linkRepository.checkConnection(doctorId, patientId);
    }

    @Override
    public Boolean requestDoctorToPatient(String doctorId, String patientId) {
        return linkRepository.requestDoctorToPatient(doctorId, patientId);
    }

    @Override
    public Boolean deleteLinkPatient(String doctor, String patientId) {
        return linkRepository.deleteLinkPatient(doctor, patientId);
    }

    @Override
    public Boolean deleteLinkDoctor(String doctor, String patientId) {
        return linkRepository.deleteLinkDoctor(doctor, patientId);
    }

    @Override
    public List<DoctorResponse> getAllDoctorOfPatient(String patientId) {
        List<String> doctorIds = linkRepository.getAllDoctorsForPatientId(patientId);
        return doctorService.findAll(doctorIds);
    }

    @Override
    public List<PatientResponse> getAllPatientOfDoctor(String doctorId) {
        List<String> patientIds = linkRepository.getAllPatientForDoctorId(doctorId);
        return patientService.findAll(patientIds);
    }
}