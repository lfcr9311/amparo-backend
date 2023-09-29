package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.UserTokenEntity;
import br.com.amparo.backend.dto.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserTokenRepository {

    private static final Logger log = LoggerFactory.getLogger(UserTokenRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserTokenRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserTokenEntity> findUserByEmail(String email) {
        try {
            String sql = """
                    SELECT u.id, u.email, u.name,
                        password, salt,
                        (d.crm is not null) as is_doctor,
                        (p.cpf is not null) as is_patient
                    FROM "User" u
                        left join "Doctor" d on u.id = d.id
                        left join "Patient" p on p.id = u.id
                    WHERE u.email = :email;
                    """;
            MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of("email", email));
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, buildRowMapper()));
        } catch (DataAccessException ex) {
            log.error("Error trying to get tokeUser for email: " + email, ex);
            return Optional.empty();
        }
    }

    private RowMapper<UserTokenEntity> buildRowMapper() {
        return (rs, rowNumber) -> {
            List<String> roles = new ArrayList<>();
            String id = rs.getString("id");
            String email = rs.getString("email");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String salt = rs.getString("salt");
            boolean isDoctor = rs.getBoolean("is_doctor");
            boolean isPatient = rs.getBoolean("is_patient");
            if (isDoctor) roles.add(UserType.DOCTOR.getRoleName());
            if (isPatient) roles.add(UserType.PATIENT.getRoleName());
            return new UserTokenEntity(id, email, name, password, salt, roles);
        };
    }
}
