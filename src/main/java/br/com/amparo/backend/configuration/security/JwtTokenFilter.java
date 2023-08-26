package br.com.amparo.backend.configuration.security;

import br.com.amparo.backend.configuration.security.domain.TokenUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.Optional;

public class JwtTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    public JwtTokenFilter() {
        this.setAuthenticationManager(buildAuthManager(userRepository, tokenService));
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map(it -> it.replace("Bearer ", ""))
                .orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

    private AuthenticationManager buildAuthManager(UserRepository userRepository, JwtTokenService tokenService) {
        return auth -> {
            String jwtToken = auth.getPrincipal().toString();
            TokenUser tokenUser = tokenService.decript(jwtToken);

        };
    }
}
