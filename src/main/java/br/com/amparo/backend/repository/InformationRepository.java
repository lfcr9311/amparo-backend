package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Information;
import br.com.amparo.backend.dto.information.InformationResponse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
public class InformationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InformationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InformationResponse create(Information information, String id) {
        try {
            String sql = """
                    INSERT INTO "Information" ("title", "link", "image", "description", "id_doctor", "created_at")
                    VALUES (
                    :title, 
                    :link, 
                    :image, 
                    :description, 
                    :id,
                    Now()
                    );
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            param.addValue("title", information.getTitle());
            param.addValue("link", information.getLink());
            param.addValue("image", information.getImage());
            param.addValue("description", information.getDescription());
            jdbcTemplate.update(sql, param);
            return new InformationResponse(information.getTitle(), information.getLink(), information.getImage(), information.getDescription(), information.getCreatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationResponse> findAll() {
        try {
            String sql = """
                    SELECT *
                    FROM "Information"
                    ORDER BY "created_at" DESC
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource();
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationResponse(
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationResponse> findByTitle(String title) {
        try {
            String sql = """
                    SELECT "title", "link", "image", "description", "created_at"
                    FROM "Information"
                    WHERE "title" ILIKE :title
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "title", "%" + title + "%"
            ));
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationResponse(
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<InformationResponse> orderByDate() {
        try {
            String sql = """
                    SELECT *
                    FROM "Information"
                    ORDER BY "created_at" DESC
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource();
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new InformationResponse(
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getDate("created_at")
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
