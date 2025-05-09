package com.clubeevantagens.authmicroservice.app.dto.request;

import com.clubeevantagens.authmicroservice.domain.entity.ResetPasswordToken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import javax.persistence.Embedded;

@Schema(name = "ResetPasswordRequest", description = "DTO para redefinição de senha com token")
public record ResetPasswordRequestDto (
        @Schema(description = "Token de redefinição de senha",
                example = "d4b5f3a8-1e3d-4c8f-b5e2-6a9b7c8d9e0f",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Embedded
        ResetPasswordToken resetPasswordToken,

        @Schema(description = "Nova senha do usuário",
                example = "NovaSenha@123",
                minLength = 8,
                maxLength = 20,
                pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                message = "Password must be 8-20 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)."
        )
        String password
) {
}
