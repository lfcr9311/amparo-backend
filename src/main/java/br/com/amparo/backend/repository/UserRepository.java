package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.exception.UserOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class UserRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        try {
            String sql = """
                    SELECT "id",
                        "email",
                        "password",
                        "salt",
                        "name",
                        "cellphone",
                        "profile_picture",
                        "is_anonymous"
                    FROM "User"
                    WHERE email = :email
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource("email", email);
            List<User> users = jdbcTemplate.query(sql, param, buildRowMapper());
            if (users.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(users.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to get user with email: " + email, e);
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findUserById (String id) {
        try {
            String sql = """
                    SELECT "id",
                        "email",
                        "password",
                        "salt",
                        "name",
                        "cellphone",
                        "profile_picture",
                        "is_anonymous"
                    FROM "User"
                    WHERE id = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource("id", UUID.fromString(id));
            List<User> users = jdbcTemplate.query(sql, param, buildRowMapper());
            if (users.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(users.get(0));
            }
        } catch (DataAccessException e) {
            log.error("Error trying to get user with id: " + id + " Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public String create(User user, SaltedPassword password) {
        try {
            String sql = """
                INSERT INTO "User" ("email","password","salt","name","cellphone","is_anonymous")
                values (
                    :email,
                    :password,
                    :salt,
                    :name,
                    :cellphone,
                    :is_anonymous
                )
                returning id
                """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "email",user.getEmail(),
                    "password",password.encryptedPassword(),
                    "salt",password.salt(),
                    "name",user.getName(),
                    "is_anonymous",false
            ));
            param.addValue("cellphone", user.getCellphone());
            return jdbcTemplate.queryForObject(sql, param, String.class);
        } catch (DataAccessException e) {
            log.error("Error trying to create user: " + user.getEmail(), e);
            throw new UserOperationException(user.getEmail(), user.getName(), user.getCellphone(), e);
        }
    }

    public Optional<User> updateUser(User user){
        try {
            String sql = """
                    UPDATE "User"
                    SET name = :name,
                        email = :email,
                        profile_picture = :profilePicture,
                        cellphone = :cellphone
                    WHERE "id" = :id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id",UUID.fromString(user.getId()),
                    "name",user.getName(),
                    "email",user.getEmail(),
                    "cellphone",user.getCellphone()
            ));
            param.addValue("profilePicture", user.getProfilePicture());
            jdbcTemplate.update(sql, param);
            return findUserById(user.getId());
        } catch (DataAccessException e) {
            log.error("Error trying to update user: " + user.getId(), e);
            throw new UserOperationException(user.getEmail(), user.getName(), user.getCellphone(), e);
        }
    }

    private RowMapper<User> buildRowMapper() {
        return (rs, rowNum) -> {
            String id = rs.getString("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String salt = rs.getString("salt");
            String name = rs.getString("name");
            String cellphone = rs.getString("cellphone");
            String profile_picture = rs.getString("profile_picture");
            boolean is_anonymous = rs.getBoolean("is_anonymous");
            return new User(id, email, password, salt, name, cellphone, profile_picture, is_anonymous);
        };
    }
}
