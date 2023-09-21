package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.User;

import java.util.Optional;

public interface UserService {
    boolean alreadyExistsEmail(String email);

    Optional<User> updateUser(User user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

}
