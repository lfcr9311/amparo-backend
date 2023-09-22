package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    @Override
    public boolean alreadyExistsEmail(final String email) {
        return email != null;
    }

    @Override
    public Optional<User> updateUser(User user) {
        return repository.updateUser(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findUserById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
