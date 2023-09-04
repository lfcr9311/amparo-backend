package br.com.amparo.backend.service;
import br.com.amparo.backend.domain.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean alreadyExistsEmail(String email);

    boolean alreadyExistsId(UUID id);

    Optional<User> findByName(String name);

    Optional<User> loadUserByUsername(String username) throws UsernameNotFoundException;
}

