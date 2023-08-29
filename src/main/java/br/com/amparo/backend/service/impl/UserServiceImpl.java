package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean alreadyExistsEmail(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
