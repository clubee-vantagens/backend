package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.infra.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.infra.exception.security.InvalidTokenException;
import com.clubeevantagens.authmicroservice.domain.entity.RefreshToken;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.LoginRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.TokenResponseDto;
import com.clubeevantagens.authmicroservice.app.repository.RefreshTokenRepository;
import com.clubeevantagens.authmicroservice.app.repository.UserRepository;
import com.clubeevantagens.authmicroservice.infra.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepo;
    private final UserRepository userRepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            RefreshTokenRepository refreshTokenRepo,
            UserRepository userRepo,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager
    ) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtils.generateToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        // Salva refresh resetPasswordToken no banco
        refreshTokenRepo.save(new RefreshToken(
                refreshToken,
                user,
                Instant.now().plusSeconds(jwtUtils.getExpiresIn()),
                false
        ));

        return new TokenResponseDto(
                accessToken,
                refreshToken,
                jwtUtils.getExpiresIn(),
                "Bearer"
        );
    }

    @Transactional
    public TokenResponseDto generateRefreshToken(String refreshToken) {
        Jwt jwt = jwtUtils.validateRefreshToken(refreshToken);

        RefreshToken storedToken = refreshTokenRepo.findById(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh resetPasswordToken."));

        if (storedToken.isRevoked() || storedToken.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidTokenException("Refresh resetPasswordToken expired or invalid.");
        }

        User user = userRepo.findById(Long.parseLong(jwt.getSubject()))
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        String newAccessToken = jwtUtils.generateToken((Authentication) user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        storedToken.setRevoked(true);
        refreshTokenRepo.save(new RefreshToken(
                newRefreshToken,
                user,
                Instant.now().plusSeconds(jwtUtils.getExpiresIn()),
                false
        ));

        return new TokenResponseDto(
                newAccessToken,
                newRefreshToken,
                jwtUtils.getExpiresIn(),
                "Bearer"
        );
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepo.deleteById(refreshToken);
    }

    @Transactional
    public void logoutAll(Long userId) {
        refreshTokenRepo.deleteByUserId(userId);
    }
}