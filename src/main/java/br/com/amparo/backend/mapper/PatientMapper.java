package br.com.amparo.backend.mapper;


import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.record.PatientRegistrationData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {
    PatientMapper MAPPER = Mappers.getMapper(PatientMapper.class);

    PatientRegistrationData mapToPatientRegistrationData(Patient patient);

    Patient mapToPatient(PatientRegistrationData patientRegistrationData);
}