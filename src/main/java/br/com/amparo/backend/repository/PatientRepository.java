package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.dto.PatientResponse;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log
public class PatientRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PatientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Patient patient) {
        try {
            String sql = """
                    INSERT INTO "Patient" ("id", "cpf")
                    values (
                        :id,
                        :cpf
                    )
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(patient.getId()),
                    "cpf", patient.getCpf()
            ));
            jdbcTemplate.update(sql, param);
            return true;
        } catch (DataAccessException e) {
            log.warning("Error trying to create patient: " + patient.getId() + " Error: " + e.getMessage());
            return false;
        }
    }

    public Optional<PatientResponse> findByCpf(String cpf) {
        try {
            String sql = """
                    SELECT p."id"            as "id",
                           p.cpf             as "cpf",
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
            PatientResponse patientResponse = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new PatientResponse(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cellphone"),
                    rs.getString("profilePicture"),
                    rs.getBoolean("isAnonymous"),
                    rs.getString("cpf")
            ));

            if (patientResponse == null) {
                return Optional.empty();
            }

            return Optional.of(patientResponse);
        } catch (DataAccessException e) {
            log.warning("Error trying to find patient by cpf: " + cpf + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

}
