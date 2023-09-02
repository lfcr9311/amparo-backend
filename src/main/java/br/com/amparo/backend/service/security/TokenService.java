package br.com.amparo.backend.service.security;

import br.com.amparo.backend.configuration.security.domain.ApiUser;
import br.com.amparo.backend.configuration.security.domain.TokenUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(TokenUser apiUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("amparo-api")
                    .withSubject(apiUser.subject())
                    .withClaim("roles", apiUser.roles())
                    .withClaim("email", apiUser.email())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public ApiUser validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT =  JWT.require(algorithm).build().verify(token);
            String id = decodedJWT.getSubject();
            String email = decodedJWT.getClaim("email").asString();
            List<String> authorities = decodedJWT.getClaim("roles").asList(String.class);

            return new ApiUser(id, email, token, authorities.stream().map(SimpleGrantedAuthority::new).toList());
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Error while validating token", e);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}