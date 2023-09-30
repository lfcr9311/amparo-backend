package br.com.amparo.backend.repository;

import br.com.amparo.backend.exception.LinkOperationalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

@Slf4j
public class LinkRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public LinkRepository (NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean linkDoctorToPatient(String doctorId, String patientId){
        try{
            String sql = """
                    INSERT INTO "Doctor_Patient" ("id_doctor", "id_patient")
                    VALUES (
                        :id_doctor,
                        :id_patient
                    )
                    RETURNING "id";
                    """;
            jdbcTemplate.update(sql, Map.of(
                    "id_doctor", doctorId,
                    "id_patient", patientId
            ));
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to link doctor: " + doctorId + " to " +
                    "patient: " + patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);

        }
    }
}
