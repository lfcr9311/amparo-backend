package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import br.com.amparo.backend.exception.InformationCreationException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
public class InformationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InformationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InformationResponse create(Information information, String doctorId) {
        try {
            String sql = """
                    INSERT INTO "Information" ("title", "link", "image", "description", "id_doctor")
                    VALUES (
                    :title, 
                    :link, 
                    :image, 
                    :description, 
                    :idDoctor)
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "idDoctor", UUID.fromString(doctorId)
            ));
            param.addValue("title", information.getTitle());
            param.addValue("link", information.getLink());
            param.addValue("image", information.getImage());
            param.addValue("description", information.getDescription());
            jdbcTemplate.update(sql, param);
            return new InformationResponse(information.getTitle(), information.getLink(), information.getImage(), information.getDescription());
        } catch (DataAccessException e) {
            log.error("Error on create information: ", e.getMessage());
            throw new InformationCreationException(e, information.getTitle(), information.getLink(), information.getImage(), information.getDescription());
        }
    }

    public List<InformationResponse> findAll(String id) {
        try {
            String sql = """
                    SELECT *
                    FROM "Information"
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", id
            ));
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationResponse(
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description")
            ));
        } catch (DataAccessException e) {
            log.error("Error on find information: ", e.getMessage());
            return new ArrayList<>();
        }
    }
}
