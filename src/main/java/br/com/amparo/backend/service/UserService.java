package br.com.amparo.backend.service;

import br.com.amparo.backend.domain.entity.Patient;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean alreadyExistsEmail(String email);
    UserDetails loadUserByUsername(String username);

    String updateUser(Patient patient);

}
