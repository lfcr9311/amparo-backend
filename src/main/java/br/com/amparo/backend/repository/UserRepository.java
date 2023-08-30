package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
