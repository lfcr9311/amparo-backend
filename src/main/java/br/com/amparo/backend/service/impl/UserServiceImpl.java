package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> updateUser(User user) {
        return repository.updateUser(user);
    }
}
