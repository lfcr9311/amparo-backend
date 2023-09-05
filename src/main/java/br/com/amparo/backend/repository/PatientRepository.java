package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

public class PatientRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

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
                    "id", patient.getId(),
                    "cpf", patient.getCpf()

            ));
            jdbcTemplate.update(sql, param);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
