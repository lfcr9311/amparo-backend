package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean alreadyExistsEmail(final String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public boolean alreadyExistsId(UUID id) {
        return false;
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
