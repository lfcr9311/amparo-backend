package br.com.amparo.backend.repository;
import br.com.amparo.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
=======
import org.springframework.security.core.userdetails.UserDetails;

>>>>>>> 9d449fb9b1d08d16b97ceb2bcab48bfe8bfc834c
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
<<<<<<< HEAD
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    Optional<User> findById(UUID id);
=======
    UserDetails findByEmail(String email);
>>>>>>> 9d449fb9b1d08d16b97ceb2bcab48bfe8bfc834c
}
