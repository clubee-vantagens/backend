package com.clubeevantagens.authmicroservice.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "RefreshTokenRequest", description = "DTO para renovação do token de acesso")
public record RefreshRequestDto(
        @Schema(description = "Token de refresh válido",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String refreshToken
) {}