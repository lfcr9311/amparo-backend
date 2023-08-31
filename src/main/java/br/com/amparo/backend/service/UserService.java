package br.com.amparo.backend.service;


import br.com.amparo.backend.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean alreadyExistsEmail(String email);

    boolean alreadyExistsId(UUID id);

    Optional<User> findByName(String name);
}
