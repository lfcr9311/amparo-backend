package br.com.amparo.backend.repository;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class DosageRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DosageRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<DosageResponse> addDosage(String patientId, String medicineId, AddDosageRequest request) {
        try {
            String sql = """
                INSERT INTO "Dosage" ("id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date")
                VALUES (
                    :id_patient,
                    :id_medicine,
                    :quantity,
                    :initial_hour,
                    :frequency,
                    :final_date
                )
                RETURNING "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = :id_medicine) AS "medicine_name";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id_patient", UUID.fromString(patientId),
                    "id_medicine", UUID.fromString(medicineId),
                    "quantity", request.quantity()
            ));
            param.addValue("initial_hour", Timestamp.valueOf(request.initial_hour()));
            param.addValue("frequency", request.frequency());
            param.addValue("final_date", Timestamp.valueOf(request.final_date()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getString("medicine_name")
            ));
            if (dosage.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(dosage.get(0));
            }
        } catch(Exception e) {
            log.error("Error trying to add dosage: " + patientId, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<DosageResponse> editDosage(String dosageId, EditDosageRequest request) {
        try {
            String sql = """
                UPDATE "Dosage"
                SET "quantity" = :quantity,
                    "initial_hour" = :initial_hour,
                    "frequency" = :frequency,
                    "final_date" = :final_date
                WHERE "id" = :id
                RETURNING "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = :id_medicine) AS "medicine_name";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId),
                    "quantity", request.quantity()
            ));
            param.addValue("frequency", request.frequency());
            param.addValue("final_date", Timestamp.valueOf(request.final_date()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getString("medicine_name")
            ));
            if (dosage.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(dosage.get(0));
            }
        } catch(Exception e) {
            log.error("Error trying to edit dosage: " + dosageId, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<DosageResponse> getDosage(String dosageId) {
        try {
            String sql = """
                SELECT "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date",
                       (SELECT "name" FROM "Medicine" WHERE "id" = "id_medicine") AS "medicine_name"
                FROM "Dosage"
                WHERE "id" = :id 
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId)
            ));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getString("medicine_name")
            ));
            if (dosage.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(dosage.get(0));
            }
        } catch(Exception e) {
            log.error("Error trying to get dosage: " + dosageId, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<DosageResponse> deleteDosage(DosageResponse dosage) {
        try {
            String sql = """
                DELETE FROM "Dosage"
                WHERE "id" = :id
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosage.id())
            ));
            jdbcTemplate.update(sql, param);
            return Optional.of(dosage);
        } catch (Exception e) {
            log.error("Error trying to delete dosage: " + dosage.id(), e);
            throw new RuntimeException(e);
        }
    }
}
