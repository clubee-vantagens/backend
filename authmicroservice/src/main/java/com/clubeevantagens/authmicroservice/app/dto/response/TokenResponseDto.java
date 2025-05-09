package com.clubeevantagens.authmicroservice.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TokenResponse", description = "DTO de resposta de autenticação")
public record TokenResponseDto(
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String accessToken,

        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String refreshToken,

        @Schema(description = "Tempo de expiração em segundos", example = "3600")
        long expiresIn,

        @Schema(example = "Bearer") String tokenType
) {}