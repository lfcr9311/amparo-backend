package br.com.amparo.backend.service.security;

import br.com.amparo.backend.domain.security.ApiUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static ApiUser getApiUser() {
        return ((ApiUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static String getCurrentUserId() {
        return getApiUser().getId();
    }
}
