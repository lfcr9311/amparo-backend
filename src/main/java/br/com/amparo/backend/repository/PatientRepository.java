package br.com.amparo.backend.repository;

import br.com.amparo.backend.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByUser_Id(final UUID userId);

    Optional<Patient> findByCpf(final String cpf);
}
