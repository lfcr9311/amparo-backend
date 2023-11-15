package br.com.amparo.backend.repository;

import br.com.amparo.backend.dto.dosage.AddDosageRequest;
import br.com.amparo.backend.dto.dosage.DosageResponse;
import br.com.amparo.backend.dto.dosage.EditDosageRequest;
import br.com.amparo.backend.exception.CreationException;
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

    public Optional<DosageResponse> addDosage(String patientId, int medicineId, AddDosageRequest request) {
        try {
            String sql = """
                INSERT INTO "Dosage" ("id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date")
                VALUES (
                    :idPatient,
                    :idMedicine,
                    :quantity,
                    :initialHour,
                    :frequency,
                    :finalDate
                )
                RETURNING "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = "id_medicine") AS "medicineName";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "idPatient", UUID.fromString(patientId),
                    "idMedicine", medicineId,
                    "quantity", request.quantity()
            ));
            param.addValue("initialHour", request.initialHour());
            param.addValue("frequency", request.frequency());
            param.addValue("finalDate", request.finalDate() == null ? null : Timestamp.valueOf(request.finalDate()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getString("frequency"),
                    rs.getTimestamp("final_date") == null ? null : rs.getTimestamp("final_date").toLocalDateTime(),
                    null,
                    rs.getString("medicineName")
            ));
            if (dosage.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(dosage.get(0));
            }
        } catch(Exception e) {
            log.error("Error trying to add dosage: " + patientId, e);
            throw new CreationException(e);
        }
    }

    public Optional<DosageResponse> editDosage(String dosageId, EditDosageRequest request, String patientId) {
        try {
            String sql = """
                UPDATE "Dosage"
                SET "quantity" = :quantity,
                    "frequency" = :frequency,
                    "final_date" = :finalDate,
                    "id_medicine" = :idMedicine,
                    "last_date" = :last_date
                WHERE "id" = :id AND "id_patient" = :patientId
                RETURNING "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date", "last_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = "id_medicine") AS "medicineName";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId),
                    "patientId", UUID.fromString(patientId),
                    "quantity", request.quantity(),
                    "idMedicine", request.medicineId()
            ));
            param.addValue("last_Date", request.lastConsumedDate());
            param.addValue("frequency", request.frequency());
            param.addValue("finalDate", request.finalDate() == null ? null : Timestamp.valueOf(request.finalDate()));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getString("frequency"),
                    rs.getTimestamp("final_date") == null ? null : rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getDate("last_date") == null ? null : rs.getDate("last_date").toLocalDate(),
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

    public Optional<DosageResponse> getDosage(String dosageId, String patientId) {
        try {
            String sql = """
                SELECT "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date", "last_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = "id_medicine") AS "medicineName"
                FROM "Dosage"
                WHERE "id_patient" = :patientId AND "id" = :id
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosageId),
                    "patientId", UUID.fromString(patientId)
            ));

            List<DosageResponse> dosage = jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getString("frequency"),
                    rs.getTimestamp("final_date") == null ? null : rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getDate("last_date") == null ? null : rs.getDate("last_date").toLocalDate(),
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

    public int deleteDosage(DosageResponse dosage) {
        try {
            String sql = """
                DELETE FROM "Dosage"
                WHERE "id" = :id AND "id_patient" = :patientId
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(dosage.id()),
                    "patientId", UUID.fromString(dosage.idPatient())
            ));
            return jdbcTemplate.update(sql, param);
        } catch (Exception e) {
            log.error("Error trying to delete dosage: " + dosage.id(), e);
            throw new RuntimeException(e);
        }
    }

    public List<DosageResponse> listDosage(String patientId){
        try{
            String sql = """
                SELECT "id", "id_patient", "id_medicine", "quantity", "initial_hour", "frequency", "final_date", "last_date",
                          (SELECT "name" FROM "Medicine" WHERE "id" = "id_medicine") AS "medicineName"
                FROM "Dosage"
                WHERE "id_patient" = :patientId
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "patientId", UUID.fromString(patientId)
            ));
            return jdbcTemplate.query(sql, param, (rs, rowNum) -> new DosageResponse(
                    rs.getString("id"),
                    rs.getString("id_patient"),
                    rs.getString("id_medicine"),
                    rs.getString("quantity"),
                    rs.getTimestamp("initial_hour").toLocalDateTime(),
                    rs.getString("frequency"),
                    rs.getTimestamp("final_date") == null ? null : rs.getTimestamp("final_date").toLocalDateTime(),
                    rs.getDate("last_date") == null ? null : rs.getDate("last_date").toLocalDate(),
                    rs.getString("medicineName")
            ));
        } catch (Exception e){
            log.error("Error trying to list dosage from patient with id: " + patientId, e);
            throw new RuntimeException(e);
        }
    }
}
