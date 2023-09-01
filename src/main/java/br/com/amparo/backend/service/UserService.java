package br.com.amparo.backend.service;

<<<<<<< HEAD

import br.com.amparo.backend.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean alreadyExistsEmail(String email);

    boolean alreadyExistsId(UUID id);

    Optional<User> findByName(String name);
=======
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean alreadyExistsEmail(String email);
    UserDetails loadUserByUsername(String username);

>>>>>>> 9d449fb9b1d08d16b97ceb2bcab48bfe8bfc834c
}
