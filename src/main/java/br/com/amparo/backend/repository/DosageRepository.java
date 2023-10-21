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
                INSERT INTO "Dosage" ("idPatient", "idMedicine", "quantity", "initialHour", "frequency", "finalDate")
                VALUES (
                    :idPatient,
                    :idMedicine,
                    :quantity,
                    :initialHour,
                    :frequency,
                    :finalDate
                )
                RETURNING "id", "idPatient", "idMedicine", "quantity", "initialHour", "frequency", "finalDate",
                          (SELECT "name" FROM "Medicine" WHERE "id" = :idMedicine) AS "medicineName";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "idPatient", UUID.fromString(patientId),
                    "idMedicine", UUID.fromString(medicineId),
                    "quantity", request.quantity()
            ));
            param.addValue("initialHour", Timestamp.valueOf(request.initialHour()));
            param.addValue("frequency", request.frequency());
            param.addValue("finalDate", Timestamp.valueOf(request.finalDate()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("idPatient"),
                    rs.getString("idMedicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initialHour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("finalDate").toLocalDateTime(),
                    rs.getString("medicineName")
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
                    "initialHour" = :initialHour,
                    "frequency" = :frequency,
                    "finalDate" = :finalDate
                WHERE "id" = :id
                RETURNING "id", "idPatient", "idMedicine", "quantity", "initialHour", "frequency", "finalDate",
                          (SELECT "name" FROM "Medicine" WHERE "id" = :idMedicine) AS "medicineName";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId),
                    "quantity", request.quantity()
            ));
            param.addValue("frequency", request.frequency());
            param.addValue("finalDate", Timestamp.valueOf(request.finalDate()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("idPatient"),
                    rs.getString("idMedicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initialHour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("finalDate").toLocalDateTime(),
                    rs.getString("medicineName")
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
                SELECT "id", "idPatient", "idMedicine", "quantity", "initialHour", "frequency", "finalDate",
                       (SELECT "name" FROM "Medicine" WHERE "id" = "idMedicine") AS "medicineName"
                FROM "Dosage"
                WHERE "id" = :id 
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId)
            ));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("idPatient"),
                    rs.getString("idMedicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initialHour").toLocalDateTime(),
                    rs.getInt("frequency"),
                    rs.getTimestamp("finalDate").toLocalDateTime(),
                    rs.getString("medicineName")
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
                AND "id_patient" = :patientId
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosage.id()),
                    "patientId", UUID.fromString(dosage.idPatient())
            ));
            jdbcTemplate.update(sql, param);
            return Optional.of(dosage);
        } catch (Exception e) {
            log.error("Error trying to delete dosage: " + dosage.id(), e);
            throw new RuntimeException(e);
        }
    }
}
