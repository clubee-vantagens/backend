package com.clubeevantagens.authmicroservice.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(name = "UpdatePasswordRequest", description = "DTO para atualização de senha com validação")
public record UpdatePasswordRequestDto(
        @Schema(description = "Senha atual do usuário",
                example = "SenhaAntiga@123",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String oldPassword,

        @Schema(description = "Nova senha do usuário",
                example = "NovaSenha@456",
                minLength = 8,
                maxLength = 20,
                pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                message = "Password must be 8-20 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)."
        )
        String newPassword
) {
}
