package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationFindResponse;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.dto.information.InformationToUpdateResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Slf4j
public class InformationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InformationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InformationResponse create(Information information, String docId) {
        try {
            String sql = """
                    INSERT INTO "Information" ("title", "link", "image", "description", "id_doctor", "created_at")
                    VALUES (
                    :title,
                    :link,
                    :image,
                    :description,
                    :id,
                    NOW()
                    )
                    returning id, id_doctor;
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(docId)
            ));
            param.addValue("title", information.getTitle());
            param.addValue("link", information.getLink());
            param.addValue("image", information.getImage());
            param.addValue("description", information.getDescription());
            String informationId = jdbcTemplate.queryForObject(sql, param, String.class);
            return new InformationResponse(informationId, information.getTitle(), information.getLink(),
                    information.getImage(), docId, information.getDescription(),
                    LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationFindResponse> findAll() {
        try {
            String sql = """
                    SELECT i.*, u.name, d.crm, d.uf
                    FROM "Information" i
                    LEFT JOIN "User" u ON i.id_doctor = u.id
                    LEFT JOIN "Doctor" d ON i.id_doctor = d.id
                    ORDER BY i."created_at" DESC
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource();
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationFindResponse(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getString("name"),
                    rs.getString("id_doctor"),
                    rs.getString("crm"),
                    rs.getString("uf")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationFindResponse> findByTitle(String title) {
        try {
            String sql = """
                    SELECT i.*, u.name, d.crm, d.uf
                    FROM "Information" i
                    LEFT JOIN "User" u ON i.id_doctor = u.id
                    LEFT JOIN "Doctor" d ON i.id_doctor = d.id
                    WHERE "title" ILIKE :title
                    ORDER BY i."created_at" DESC
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "title", "%" + title + "%"
            ));
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationFindResponse(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getString("name"),
                    rs.getString("id_doctor"),
                    rs.getString("crm"),
                    rs.getString("uf")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<InformationResponse> findInformationById(String id) {
        try {
            String sql = """
                    SELECT i.*
                    FROM "Information" i
                    WHERE i.id = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            List<InformationResponse> information = jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationResponse(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("id_doctor"),
                    rs.getString("description"),
                    rs.getDate("created_at").toLocalDate()
            ));
            if (information.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(information.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find information by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<InformationResponse> updateInformation(String id, InformationToUpdateResponse informationRequest, String doctorId) {
        try {
            String sql = """
                    UPDATE "Information"
                    SET "title" = :title,
                        "link" = :link,
                        "image" = :image,
                        "description" = :description,
                        "created_at" =  NOW()
                    WHERE "id" = :id AND "id_doctor" = :doctorId
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            param.addValue("doctorId", UUID.fromString(doctorId));
            param.addValue("title", informationRequest.title());
            param.addValue("link", informationRequest.link());
            param.addValue("image", informationRequest.image());
            param.addValue("description", informationRequest.description());
            jdbcTemplate.update(sql, param);
            return findInformationById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationFindResponse> findAllDoctor(String idDoctor) {
        try {
            String sql = """
                    SELECT i.*, u.name, d.crm, d.uf
                    FROM "Information" i
                    LEFT JOIN "User" u ON i.id_doctor = u.id
                    LEFT JOIN "Doctor" d ON i.id_doctor = d.id
                    WHERE i.id_doctor = :idDoctor
                    ORDER BY i."created_at" DESC
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("idDoctor", UUID.fromString(idDoctor));
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationFindResponse(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getString("name"),
                    rs.getString("id_doctor"),
                    rs.getString("crm"),
                    rs.getString("uf")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

