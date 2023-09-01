package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.User;
import br.com.amparo.backend.repository.UserRepository;
import br.com.amparo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
<<<<<<< HEAD

        return userRepository.findByEmail(email).isPresent();
=======
        UserDetails user = userRepository.findByEmail(email);
        return user != null;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
>>>>>>> 9d449fb9b1d08d16b97ceb2bcab48bfe8bfc834c
    }
    public Optional<User> findByName(String name) {

        return userRepository.findByName(name);
    }
    public boolean alreadyExistsId(UUID id) {
         return userRepository.findById(id).isPresent();
        }
}
