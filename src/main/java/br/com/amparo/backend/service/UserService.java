package br.com.amparo.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean alreadyExistsEmail(String email);
    UserDetails loadUserByUsername(String username);

}
