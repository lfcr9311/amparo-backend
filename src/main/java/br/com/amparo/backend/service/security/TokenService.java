package br.com.amparo.backend.service.security;

import br.com.amparo.backend.domain.security.ApiUser;
import br.com.amparo.backend.domain.security.TokenUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
public class TokenService {
    private String secret;

    public TokenService(String secret) {
        this.secret = secret;
    }
    public String generateToken(TokenUser apiUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("amparo-api")
                    .withSubject(apiUser.subject())
                    .withClaim("roles", apiUser.roles())
                    .withClaim("email", apiUser.email())
                    .withClaim("name", apiUser.name())
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
            String name = decodedJWT.getClaim("name").asString();
            String email = decodedJWT.getClaim("email").asString();
            List<String> authorities = decodedJWT.getClaim("roles").asList(String.class);

            return new ApiUser(id, email, name, token, authorities.stream().map(SimpleGrantedAuthority::new).toList());
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Error while validating token", e);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
