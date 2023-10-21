package br.com.amparo.backend.repository;

import br.com.amparo.backend.dto.exam.CreateExamRequest;
import br.com.amparo.backend.dto.exam.ExamResponse;
import br.com.amparo.backend.dto.exam.ExamToUpdateRequest;
import br.com.amparo.backend.exception.ExamCreationException;
import br.com.amparo.backend.exception.ExamOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class ExamRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExamRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ExamResponse> addExam(CreateExamRequest exam, String id) {
        try {
            String sql = """
                INSERT INTO "Exam" ("idPatient", "exam_date", "description", "is_done", "exam_image", "exam_file")
                VALUES (
                    :idPatient,
                    :exam_date,
                    :description,
                    :is_done,
                    :exam_image,
                    :exam_file
                )
                RETURNING "id";
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "idPatient", UUID.fromString(id),
                    "exam_date", exam.exam_date(),
                    "description", exam.description(),
                    "is_done", exam.is_done()
            ));
            param.addValue("exam_file", exam.file());
            param.addValue("exam_image", exam.image());

            UUID examId = jdbcTemplate.queryForObject(sql, param, UUID.class);
            return findExamById(examId.toString());
        } catch (DataAccessException e) {
            log.error("Error trying to add exam to patient: " + id + " Error: " + e.getMessage());
            throw new ExamCreationException(id, exam.description(), exam.exam_date(), exam.is_done());
        }
    }


    public Optional<ExamResponse> findExamById (String id){
        try{
            String sql = """
                    SELECT e."id"            as "id",
                           e."description"   as "description",
                           e."exam_date"     as "examDate",
                           e."is_done"       as "isDone",
                           e."idPatient"    as "patientId",
                           e."exam_image"    as "image",
                           e."exam_file"     as "file"
                    FROM "Exam" e
                    WHERE e."id" = :id
                    """;

            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id)
            ));
            ExamResponse exam = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new ExamResponse(
                    rs.getString("id"),
                    rs.getString("description"),
                    rs.getTimestamp("examDate").toLocalDateTime(),
                    rs.getBoolean("isDone"),
                    rs.getString("patientId"),
                    rs.getString("image"),
                    rs.getString("file")
            ));
            if (exam == null) {
                return Optional.empty();
            } else {
                return Optional.of(exam);
            }
        } catch (DataAccessException e) {
            log.error("Error trying to find exam by id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<ExamResponse> listDoneExams(String id, int page, int size) {
        try {
            int offset = (page - 1) * size;

            String sql = """
                SELECT e."id"            as "id",
                       e."description"   as "description",
                       e."exam_date"     as "examDate",
                       e."is_done"       as "isDone",
                       e."idPatient"    as "patientId",
                       e."exam_image"    as "image",
                       e."exam_file"     as "file"
                FROM "Exam" e
                WHERE e."idPatient" = :id
                AND e."is_done" = true
                LIMIT :size OFFSET :offset
                """;

            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id),
                    "size", size,
                    "offset", offset
            ));
            List<ExamResponse> exams = jdbcTemplate.query(sql, param, (rs, rowNum) -> new ExamResponse(
                    rs.getString("id"),
                    rs.getString("description"),
                    rs.getTimestamp("examDate").toLocalDateTime(),
                    rs.getBoolean("isDone"),
                    rs.getString("patientId"),
                    rs.getString("image"),
                    rs.getString("file")
            ));

            return exams;
        } catch (DataAccessException e) {
            log.error("Error trying to list pending exams from patient with id: " + id + " Error: " + e.getMessage());
            throw new ExamOperationException(id, "", "", "");
        }
    }

    public List<ExamResponse> listPendingExams(String id, int page, int size) {
        try {
            int offset = (page - 1) * size;

            String sql = """
                SELECT e."id"            as "id",
                       e."description"   as "description",
                       e."exam_date"     as "examDate",
                       e."is_done"       as "isDone",
                       e."idPatient"    as "patientId",
                       e."exam_image"    as "image",
                       e."exam_file"     as "file"
                FROM "Exam" e
                WHERE e."idPatient" = :id
                AND e."is_done" = false
                LIMIT :size OFFSET :offset
                """;

            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id),
                    "size", size,
                    "offset", offset
            ));
            List<ExamResponse> exams = jdbcTemplate.query(sql, param, (rs, rowNum) -> new ExamResponse(
                    rs.getString("id"),
                    rs.getString("description"),
                    rs.getTimestamp("examDate").toLocalDateTime(),
                    rs.getBoolean("isDone"),
                    rs.getString("patientId"),
                    rs.getString("image"),
                    rs.getString("file")
            ));

            return exams;
        } catch (DataAccessException e) {
            log.error("Error trying to list pending exams from patient with id: " + id + " Error: " + e.getMessage());
            throw new ExamOperationException(id, "", "", "");
        }
    }


    public Optional<ExamResponse> editExam (ExamToUpdateRequest examRequest, String id){
        try{
            String sql = """
                    UPDATE "Exam"
                    SET "description" = :description,
                        "exam_date" = :exam_date,
                        "is_done" = :is_done,
                        "exam_image" = :exam_image,
                        "exam_file" = :exam_file
                    WHERE "id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id", UUID.fromString(id),
                    "description", examRequest.description(),
                    "exam_date", examRequest.examDate(),
                    "is_done", examRequest.isDone()
            ));
            param.addValue("exam_image", examRequest.image());
            param.addValue("exam_file", examRequest.file());
            jdbcTemplate.update(sql, param);
            return findExamById(id);
        } catch (DataAccessException e) {
            log.error("Error trying to edit exam with id: " + id + " Error: " + e.getMessage());
            throw new ExamOperationException(id, examRequest.description(), "", examRequest.examDate().toString());
        }
    }
}
