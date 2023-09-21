package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.dto.DoctorResponse;
import br.com.amparo.backend.dto.PatientResponse;
import br.com.amparo.backend.exception.ApiErrorException;
import br.com.amparo.backend.exception.DoctorCreationException;
import br.com.amparo.backend.exception.PatientUpdateException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class DoctorRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public DoctorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean create(Doctor doctor) {
        try {
            String sql = """
                    INSERT INTO "Doctor" ("id", "crm", "uf")
                    values (
                        :id,
                        :crm,
                        :uf
                    )
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(doctor.getId()),
                    "crm", doctor.getCrm(),
                    "uf", doctor.getUf()
            ));

            jdbcTemplate.update(sql, param);
            return true;

        } catch (DataAccessException e) {
            log.error("Error trying to create doctor: " + doctor.getId(), e);
            throw new DoctorCreationException(doctor.getEmail(), doctor.getCrm(), doctor.getUf());
        }


    }

    public Optional<DoctorResponse> updateDoctor(Doctor doctor) {
        try {
            String sql = """
                    UPDATE "Doctor"
                    SET crm = :crm
                    WHERE "id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(doctor.getId()),
                    "crm", doctor.getCrm()
            ));
            jdbcTemplate.update(sql, param);
            return findDoctorById(doctor.getId());
        } catch (DataAccessException e) {
            log.error("Error trying to update doctor: " + doctor.getId() + " Error: " + e.getMessage());
            throw new PatientUpdateException(doctor.getEmail(), doctor.getName(), doctor.getCellphone(), e);
        }
    }

    public Optional<DoctorResponse> findDoctorById(String id) {
        try {
            String sql = """
                    SELECT d."id"            as "id",
                           d.crm             as "crm",
                           u."email"         as "email",
                           u.name            as "name",
                           u.cellphone       as "cellphone",
                           u.is_anonymous    as "isAnonymous",
                           u.profile_picture as "profilePicture",
                           d.uf              as "uf"
                    FROM "Doctor" d
                    LEFT JOIN "User" u ON u."id" = d."id"
                    WHERE d."id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            DoctorResponse doctorResponse = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new DoctorResponse(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("cellphone"),
                    rs.getString("profilePicture"),
                    rs.getBoolean("isAnonymous"),
                    rs.getString("crm"),
                    rs.getString("uf")
            ));

            if (doctorResponse == null) {
                return Optional.empty();
            }

            return Optional.of(doctorResponse);
        } catch (DataAccessException e) {
            log.error("Error trying to find doctor by id: " + id + " Error: " + e.getMessage());
            throw new ApiErrorException("Erro ao buscar médico com id: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
