package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.domain.record.SaltedPassword;
import br.com.amparo.backend.exception.UserCreationException;
import br.com.amparo.backend.exception.UserUpdateException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Optional;

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
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, param, buildRowMapper()));
        } catch (DataAccessException e) {
            log.error("Error trying to get user with email: " + email);
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
                    "cellphone",user.getCellphone(),
                    "is_anonymous",false
            ));
            return jdbcTemplate.queryForObject(sql, param, String.class);
        } catch (DataAccessException e) {
            log.error("Error trying to create user: " + user.getEmail(), e);
            throw new UserCreationException(user.getEmail(), user.getName(), user.getCellphone(), e);
        }
    }

    public String updateUser(User user){
        try {
            String sql = """
                    UPDATE "User"
                    SET name = :name,
                        email = :email,
                        password = :password,
                        profile_picture = :profilePicture,
                        cellphone = :cellphone
                    WHERE "id" = :id
                    returning id
                    """;
            MapSqlParameterSource param = new MapSqlParameterSource(Map.of(
                    "id",user.getId(),
                    "name",user.getName(),
                    "email",user.getEmail(),
                    "password",user.getPassword(),
                    "profilePicture",user.getProfilePicture(),
                    "cellphone",user.getCellphone()
            ));
            return jdbcTemplate.queryForObject(sql, param, String.class);
        } catch (DataAccessException e) {
            log.error("Error trying to update user: " + user.getId(), e);
            throw new UserUpdateException(user.getEmail(), user.getName(), user.getCellphone(), e);
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
            String profile_picture = rs.getNString("profile_picture");
            boolean is_anonymous = rs.getBoolean("is_anonymous");
            return new User(id, email, password, salt, name, cellphone, profile_picture, is_anonymous);
        };
    }
}
