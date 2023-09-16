package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Doctor;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

@Log
public class DoctorRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public DoctorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean create(Doctor doctor) {
        try {
            String sql = """
                    INSERT INTO "Doctor" ("uf", "crm")
                    values (
                        :uf,
                        :crm
                    )
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "uf", doctor.getUf(),
                    "crm", doctor.getCrm()
            ));
            jdbcTemplate.update(sql, param);
            return true;
        } catch (DataAccessException e) {
            log.warning("Error trying to create doctor: " +
                    doctor.getId() + " Error: " + e.getMessage());
            return false;
        }
    }
}
