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

import java.util.*;

@Slf4j
public class MedicineRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public MedicineRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<MedicineResponse> findMedicineById(int id) {
        try {
            String sql = """
                    SELECT  m."id" as "id",
                            m."name" as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    WHERE m."id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", id
            ));
            MedicineResponse medicineResponse = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new MedicineResponse(
                    rs.getInt(id),
                    rs.getString("name"),
                    rs.getString("leaflet")
            ));
            return Optional.ofNullable(medicineResponse);
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<MedicineResponse> findMedicineByName(String name) {
        try {
            String sql = """
                    SELECT  m."id" as "id",
                            m."name" as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    WHERE m."name" = :name
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(
                    Map.of("name", name)
            );
            MedicineResponse medicineResponse = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new MedicineResponse(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("leaflet")
            ));
            return Optional.ofNullable(medicineResponse);
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by name: " + name + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<List<MedicineIncResponse>> findAllIncompatibility(int id) {
        try {
            String sql = """
                    SELECT m."id" as "id_medicine_inc",
                            m."name" as "name_medicine_inc",
                            i."severity" as "severity"
                    FROM "Medicine" m
                    INNER JOIN "Incompatibility" i ON m."id" = i."id_medicine_inc"
                    WHERE i."id_medicine" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(
                    Map.of("id", id)
            );
            List<MedicineIncResponse> medicineIncResponse = jdbcTemplate.query(sql, param, (rs, rowNum) -> new MedicineIncResponse(
                    rs.getInt("id_medicine_inc"),
                    rs.getString("name_medicine_inc"),
                    rs.getString("severity")
            ));
            if(medicineIncResponse.isEmpty()){
                return Optional.empty();
            }else {
                return Optional.of(medicineIncResponse);
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find medicine by id: " + id + " Error: " + e.getMessage());
            return Optional.of(new ArrayList<>());
        }
    }

    public List<MedicineResponse> findAllMedicines(int pageNumber, int pageSize) {
        try {
            String sql = """
                    SELECT  m."id" as "id",
                            m."name" as "name",
                            m."leaflet" as "leaflet"
                    FROM "Medicine" m
                    LIMIT :pageSize OFFSET :offset
                    """;

            int offset = (pageNumber - 1) * pageSize;

            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("pageSize", pageSize)
                    .addValue("offset", offset);

            List<MedicineResponse> medicines = jdbcTemplate.query(sql, param, getMedicineRowMapper());
            return medicines;
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
