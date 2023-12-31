package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.patient.PatientResponse;
import br.com.amparo.backend.exception.PatientCreationException;
import br.com.amparo.backend.exception.PatientOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

@Slf4j
public class PatientRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PatientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Patient patient) {
        try {
            String sql = """
                    INSERT INTO "Patient" ("id", "cpf", "birth_date", "num_sus")
                    values (
                        :id,
                        :cpf,
                        :birth_date,
                        :num_sus
                    )
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(patient.getId()),
                    "cpf", patient.getCpf(),
                    "birth_date", patient.getBirthDate()
            ));
            param.addValue("num_sus", patient.getNumSus());
            jdbcTemplate.update(sql, param);
            return true;
        } catch (DataAccessException e) {
            log.error("Error trying to create patient: " + patient.getId() + " Error: " + e.getMessage());
            throw new PatientCreationException(e, patient.getEmail(), patient.getCpf());
        }
    }
    public Optional<PatientResponse> updatePatient(Patient patient) {
        try {
            String sql = """
                    UPDATE "Patient"
                    SET cpf = :cpf,
                        birth_date = :birthDate,
                        num_sus = :numSus
                    WHERE "id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(patient.getId()),
                    "cpf", patient.getCpf(),
                    "birthDate", patient.getBirthDate()
            ));
            param.addValue("numSus", patient.getNumSus());
            jdbcTemplate.update(sql, param);
            return findByCpf(patient.getCpf());
        } catch (DataAccessException e) {
            log.error("Error trying to update patient: " + patient.getId() + " Error: " + e.getMessage());
            throw new PatientOperationException(patient.getEmail(), patient.getCpf(), e);
        }
    }

    public Optional<PatientResponse> findByCpf(String cpf) {
        try {
            String sql = """
                    SELECT p."id"            as "id",
                           p.cpf             as "cpf",
                           p.birth_date      as "birthDate",
                           p.num_sus         as "numSus",
                           u."email"         as "email",
                           u.name            as "name",
                           u.cellphone       as "cellphone",
                           u.profile_picture as "profilePicture",
                           u.is_anonymous    as "isAnonymous"
                    FROM "Patient" p
                             LEFT JOIN "User" u ON u."id" = p."id"
                    WHERE p.cpf = :cpf
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "cpf", cpf
            ));
            List<PatientResponse> patientResponse = jdbcTemplate.query(sql, param, (rs, rowNum) -> new PatientResponse(
                    rs.getString("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cellphone"),
                    rs.getString("profilePicture"),
                    rs.getBoolean("isAnonymous"),
                    rs.getString("cpf"),
                    rs.getString("birthDate"),
                    rs.getString("numSus")
            ));
            if (patientResponse.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(patientResponse.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find patient by cpf: " + cpf + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<PatientResponse> findById(String id) {
        try {
            String sql = """
                    SELECT p."id"            as "id",
                           p.cpf             as "cpf",
                           p.birth_date      as "birthDate",
                           p.num_sus         as "numSus",
                           u."email"         as "email",
                           u.name            as "name",
                           u.cellphone       as "cellphone",
                           u.profile_picture as "profilePicture",
                           u.is_anonymous    as "isAnonymous"
                    FROM "Patient" p
                             LEFT JOIN "User" u ON u."id" = p."id"
                    WHERE p."id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            List<PatientResponse> patients = jdbcTemplate.query(sql, param, (rs, rowNum) -> new PatientResponse(
                    rs.getString("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cellphone"),
                    rs.getString("profilePicture"),
                    rs.getBoolean("isAnonymous"),
                    rs.getString("cpf"),
                    rs.getString("birthDate"),
                    rs.getString("numSus")
            ));
            if (patients.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(patients.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find patient by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<PatientResponse> findAll(List<UUID> patientIds) {
        try {
            String sql = """
                   SELECT 
                          p.id              as "id",
                          p."cpf"           as "cpf",
                          u."email"         as "email",
                          u.name            as "name",
                          u.cellphone       as "cellphone",
                          u.is_anonymous    as "isAnonymous",
                          u.profile_picture as "profilePicture",
                          p.birth_date      as "birthDate",
                          p.num_sus         as "numSus"
                   FROM "Patient" p
                   LEFT JOIN "User" u ON u."id" = p."id"
                   WHERE p."id" in (:ids)
                   """;
            return jdbcTemplate.query(sql, Map.of("ids", patientIds), getPatientResponseRowMapper());
        } catch (DataAccessException ex) {
            log.error("Error trying to get all patients baseOn Ids " + patientIds + " Error: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    private static RowMapper<PatientResponse> getPatientResponseRowMapper() {
        return (rs, rowNum) -> new PatientResponse(
                rs.getString("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("cellphone"),
                rs.getString("profilePicture"),
                rs.getBoolean("isAnonymous"),
                rs.getString("cpf"),
                rs.getString("birthdate"),
                rs.getString("numSus")
        );
    }
}
