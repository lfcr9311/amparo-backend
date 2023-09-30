package br.com.amparo.backend.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
public class LinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
}
