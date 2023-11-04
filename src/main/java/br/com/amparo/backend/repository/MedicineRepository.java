package br.com.amparo.backend.repository;

import br.com.amparo.backend.dto.medicine.MedicineIncResponse;
import br.com.amparo.backend.dto.medicine.MedicineResponse;
import br.com.amparo.backend.exception.MedicineOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MedicineRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MedicineRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<MedicineResponse> findMedicineById(int id) {
        try {
            String sql = """
                    SELECT  m."id"      as "id",
                            m."name"    as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    WHERE m."id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", id
            ));
            List<MedicineResponse> medicineResponse = jdbcTemplate.query(sql, param, (rs, rowNum) -> new MedicineResponse(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("leaflet")
            ));
            if (medicineResponse.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(medicineResponse.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<MedicineResponse> findMedicineByName(String name) {
        try {
            String sql = """
                    SELECT  m."id"      as "id",
                            m."name"    as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    WHERE m."name" ILIKE :name
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(
                    Map.of("name", "%" + name + "%")
            );

            return jdbcTemplate.query(sql, param, (rs, rowNum) ->
                    new MedicineResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("leaflet")
                    )
            );
            List<MedicineResponse> medicineResponse = jdbcTemplate.query(sql, param, (rs, rowNum) -> new MedicineResponse(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("leaflet")
            ));
            if (medicineResponse.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(medicineResponse.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by name: " + name + " Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<MedicineIncResponse> findAllIncompatibility(int id) {
        try {
            String sql = """
                    SELECT m."id"        as "id_medicine_inc",
                            m."name"     as "name_medicine_inc",
                            i."severity" as "severity"
                    FROM "Medicine" m
                    INNER JOIN "Incompatibility" i ON m."id" = i."id_medicine_inc"
                    WHERE i."id_medicine" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(
                    Map.of("id", id)
            );
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new MedicineIncResponse(
                    rs.getInt("id_medicine_inc"),
                    rs.getString("name_medicine_inc"),
                    rs.getInt("severity")
            ));
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by id: " + id + " Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<MedicineIncResponse> findIncompatibility(int id, List<Integer> medicineIds) {
        try {
            String sql = """
            SELECT
                minc.id as id,
                minc.name as medicine_name,
                i.severity
            FROM "Incompatibility" i
                JOIN "Medicine" m on i.id_medicine = m.id
                JOIN "Medicine" minc on i.id_medicine_inc = minc.id
            WHERE i.id_medicine = :id AND i.id_medicine_inc IN (:medicineIds)
            ORDER BY minc.name;
            """;
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id", id);
            param.addValue("medicineIds", medicineIds);

            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new MedicineIncResponse(
                    rs.getInt("id"),
                    rs.getString("medicine_name"),
                    rs.getInt("severity")
            ));
        } catch (DataAccessException e) {
            log.error("Error trying to find incompatibilities for medicine " + id, e);
            return new ArrayList<>();
        }
    }

    public List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize) {
        try {
            String sql = """
                    SELECT  m."id"      as "id",
                            m."name"    as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    LIMIT :pageSize OFFSET :offset
                    """;

            int offset = (pageNumber - 1) * pageSize;

            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("pageSize", pageSize)
                    .addValue("offset", offset);

            return jdbcTemplate.query(sql, param, getMedicineRowMapper());
        } catch (DataAccessException e) {
            log.error("Error trying to find medicines by page: " + pageNumber + ". Error: " + e.getMessage());
            throw new MedicineOperationException(0, "null", "null", e);
        }
    }

    private RowMapper<MedicineResponse> getMedicineRowMapper() {
        return (rs, rowNum) -> new MedicineResponse(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("leaflet")
        );
    }
}
