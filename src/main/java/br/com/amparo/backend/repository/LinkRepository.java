package br.com.amparo.backend.repository;

import br.com.amparo.backend.exception.LinkOperationalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class LinkRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public LinkRepository (NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean linkDoctorToPatient(String doctorId, String patientId) {
        try {
            String sql = """
                INSERT INTO "DoctorPatient" ("id_doctor", "id_patient")
                VALUES (
                    :id_doctor,
                    :id_patient
                )
                """;
            jdbcTemplate.update(sql, Map.of(
                    "id_doctor", UUID.fromString(doctorId),
                    "id_patient", UUID.fromString(patientId)
            ));
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to link doctor: " + doctorId + " to " + "patient: " + patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public boolean checkConnection(String doctorId, String patientId) {
        try {
            String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM "DoctorPatient"
                    WHERE "id_doctor" = :id_doctor
                    AND "id_patient" = :id_patient
                );
                """;

            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id_doctor", UUID.fromString(doctorId),
                    "id_patient", UUID.fromString(patientId)
            ));

            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, Boolean.class));
        } catch (DataAccessException e) {
            log.error("Error trying to check connection between doctor: " + doctorId + " and patient: " + patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

}
