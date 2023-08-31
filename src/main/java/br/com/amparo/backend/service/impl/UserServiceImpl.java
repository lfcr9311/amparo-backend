package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean alreadyExistsEmail(final String email) {

        return userRepository.findByEmail(email).isPresent();
    }
    public Optional<User> findByName(String name) {

        return userRepository.findByName(name);
    }
    public boolean alreadyExistsId(UUID id) {
         return userRepository.findById(id).isPresent();
        }
}
