package br.com.amparo.backend.repository;

import br.com.amparo.backend.exception.LinkOperationalException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

@Slf4j
public class LinkRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public LinkRepository (NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean requestDoctorToPatient(String doctorId, String patientId) {
        try {
            String sql = """
                INSERT INTO "DoctorPatient" ("id_doctor", "id_patient")
                VALUES (
                    :id_doctor,
                    :idPatient
                )
                """;
            jdbcTemplate.update(sql, Map.of(
                    "id_doctor", UUID.fromString(doctorId),
                    "idPatient", UUID.fromString(patientId)
            ));
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to link doctor: " + doctorId + " to " + "patient: " + patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public boolean checkConnectionRequest(String doctorId, String patientId) {
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

            return Boolean.FALSE.equals(jdbcTemplate.queryForObject(sql, param, Boolean.class));

        } catch (DataAccessException e) {
            log.error("Error trying to check connection between doctor: " + doctorId + " and patient: " +
                    patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public Boolean checkConnection(String doctorId, String patientId) {
        try {

            String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM "DoctorPatient"
                    WHERE "id_doctor" = :id_doctor
                    AND "id_patient" = :idPatient
                );
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id_doctor", UUID.fromString(doctorId),
                    "idPatient", UUID.fromString(patientId)
            ));

            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, Boolean.class));

        } catch (DataAccessException e) {
            log.error("Error trying to check connection between doctor: " + doctorId + " and patient: " +
                    patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public Boolean linkDoctorToPatient (String doctorId, String patientId){
        try {
            String sql = """
                UPDATE "DoctorPatient"
                SET "accepted" = true
                WHERE "id_doctor" = :id_doctor
                AND "id_patient" = :id_patient
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

    public Boolean deleteLinkPatient (String doctorId, String patientId){
        try {
            String sql = """
                DELETE FROM "DoctorPatient"
                WHERE "id_doctor" = :id_doctor
                AND "id_patient" = :id_patient
                """;
            jdbcTemplate.update(sql, Map.of(
                    "id_doctor", UUID.fromString(doctorId),
                    "id_patient", UUID.fromString(patientId)
            ));
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to delete link between doctor: " + doctorId + " and patient: " +
                    patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public Boolean deleteLinkDoctor (String patientId, String doctorId){
        try {
            String sql = """
                DELETE FROM "DoctorPatient"
                WHERE "id_patient" = :id_patient
                AND "id_doctor" = :id_doctor
                """;
            jdbcTemplate.update(sql, Map.of(
                    "id_patient", UUID.fromString(patientId),
                    "id_doctor", UUID.fromString(doctorId)
            ));
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to delete link between doctor: " + doctorId + " and patient: " +
                    patientId + " Error: " + e.getMessage());
            throw new LinkOperationalException(doctorId, patientId, e);
        }
    }

    public List<String> getAllDoctorsForPatientId(String patientId) {
        try {
            String sql = """
                    select id_doctor
                    from "DoctorPatient"
                    where id_patient = :id_patient
                    """;
            return jdbcTemplate.queryForList(sql, Map.of("id_patient", patientId), String.class);
        } catch (DataAccessException ex) {
            log.error("Error trying to get all doctors linked to patientId: " + patientId, ex);
            return new ArrayList<>();
        }
    }

    public List<String> getAllPatientForDoctorId(String doctorId) {
        try {
            String sql = """
                    select id_patient
                    from "DoctorPatient"
                    where id_doctor = :id_doctor
                    """;
            return jdbcTemplate.queryForList(sql, Map.of("id_doctor", doctorId), String.class);
        } catch (DataAccessException ex) {
            log.error("Error trying to get all patients linked to doctorId: " + doctorId, ex);
            return new ArrayList<>();
        }
    }
}
