package com.clubeevantagens.authmicroservice.infra.security;

import com.clubeevantagens.authmicroservice.infra.exception.security.InvalidTokenException;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class JwtUtils {

    @Value("${jwt.token.expiry}")
    private Long expiry;

    @Value("${jwt.token.refresh.expiry}")
    private Long expiryRefreshToken;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtUtils(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        List<String> roles = List.of(user.getRole().name());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("clubee-security")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("roles", roles)
                .claim("purpose", "access")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("clubee-security")
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiryRefreshToken))
                .claim("purpose", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public long getExpiresIn() {
        return expiry;
    }

    public Jwt validateRefreshToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        if (!"refresh".equals(jwt.getClaim("purpose"))) {
            throw new InvalidTokenException("Invalid resetPasswordToken purpose.");
        }
        return jwt;
    }
}
