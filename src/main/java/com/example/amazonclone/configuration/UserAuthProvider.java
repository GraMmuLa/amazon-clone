package com.example.amazonclone.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.amazonclone.dto.UserDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-value}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);
        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withClaim("id", userDto.getId())
                .withClaim("firstname", userDto.getFirstname())
                .withClaim("middlename", userDto.getMiddlename())
                .withClaim("surname", userDto.getSurname())
                .withClaim("email", userDto.getEmail())
                .withClaim("phone", userDto.getPhone())
                .withClaim("roleName", userDto.getRoleName())
                .withClaim("favouriteProductColorIds", userDto.getFavouriteProductColorIds())
                .withClaim("createdAt", userDto.getCreatedAt())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String createTokenWithTime(UserDto userDto, Long from, Long validity) {
        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withClaim("id", userDto.getId())
                .withClaim("firstname", userDto.getFirstname())
                .withClaim("middlename", userDto.getMiddlename())
                .withClaim("surname", userDto.getSurname())
                .withClaim("email", userDto.getEmail())
                .withClaim("phone", userDto.getPhone())
                .withClaim("roleName", userDto.getRoleName())
                .withClaim("favouriteProductColorIds", userDto.getFavouriteProductColorIds())
                .withClaim("createdAt", userDto.getCreatedAt())
                .withIssuedAt(Instant.ofEpochSecond(from))
                .withExpiresAt(Instant.ofEpochSecond(validity))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) throws NotFoundException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
