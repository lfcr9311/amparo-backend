package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.dto.doctor.DoctorResponse;
import br.com.amparo.backend.exception.DoctorOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

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
            throw new DoctorOperationException(doctor.getEmail(), doctor.getCrm(), doctor.getUf(), e);
        }


    }

    public Optional<DoctorResponse> updateDoctor(Doctor doctor) {
        try {
            String sql = """
                    UPDATE "Doctor"
                    SET crm = :crm,
                        uf = :uf
                    WHERE "id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(doctor.getId()),
                    "crm", doctor.getCrm(),
                    "uf", doctor.getUf()
            ));
            jdbcTemplate.update(sql, param);
            return findDoctorById(doctor.getId());
        } catch (DataAccessException e) {
            log.error("Error trying to update doctor: " + doctor.getId() + " Error: " + e.getMessage());
            throw new DoctorOperationException(doctor.getEmail(), doctor.getCrm(), doctor.getUf(), e);
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
            DoctorResponse doctorResponse = jdbcTemplate.queryForObject(sql, param, getDoctorResponseRowMapper());
            return Optional.ofNullable(doctorResponse);
        } catch (DataAccessException e) {
            log.error("Error trying to find doctor by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<DoctorResponse> findAll(List<UUID> doctorIds) {
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
                    WHERE d."id" in (:ids)
                    """;
            return jdbcTemplate.query(sql, Map.of("ids", doctorIds), getDoctorResponseRowMapper());
        } catch (DataAccessException ex) {
            log.error("Error trying to get all doctors baseOn Ids " + doctorIds, ex);
            return new ArrayList<>();
        }
    }

    private static RowMapper<DoctorResponse> getDoctorResponseRowMapper() {
        return (rs, rowNum) -> new DoctorResponse(
                rs.getString("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("cellphone"),
                rs.getString("profilePicture"),
                rs.getBoolean("isAnonymous"),
                rs.getString("crm"),
                rs.getString("uf")
        );
    }
}
