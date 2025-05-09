package com.clubeevantagens.authmicroservice.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(name = "ForgotPasswordRequest", description = "DTO para solicitação de recuperação de senha")
public record ForgotRequestDto(
        @Schema(description = "E-mail do usuário para recuperação",
                example = "usuario@example.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Email
        String email
) {
}
