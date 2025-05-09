package com.clubeevantagens.authmicroservice.infra.controller;

import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.LoginRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.RefreshRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.TokenResponseDto;
import com.clubeevantagens.authmicroservice.app.usecase.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Authentication Management", description = "Endpoints públicos para entrada, saída, etc de usuários no sistema")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> generateRefreshToken(@RequestBody @Valid RefreshRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.generateRefreshToken(dto.refreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequestDto dto) {
        authService.logout(dto.refreshToken());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/logout/all")
    public ResponseEntity<Void> logoutAll(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        authService.logoutAll(userDetails.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
