package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Patient;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    @Override
    public boolean alreadyExistsEmail(final String email) {
        return email != null;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public String updateUser(Patient patient) {
        return repository.updateUser(patient);
    }
}
