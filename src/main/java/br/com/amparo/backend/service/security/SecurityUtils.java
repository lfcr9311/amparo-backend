package br.com.amparo.backend.service.security;

import br.com.amparo.backend.configuration.security.domain.ApiUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static ApiUser getApiUser() {
        return ((ApiUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
