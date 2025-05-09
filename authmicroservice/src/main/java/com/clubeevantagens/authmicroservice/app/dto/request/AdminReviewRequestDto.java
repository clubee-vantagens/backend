package com.clubeevantagens.authmicroservice.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Schema(name = "AdminCreateReviewRequest",
        description = "DTO para criação de avaliação por administrador")
public record AdminReviewRequestDto(
        @Schema(description = "Nota da avaliação (1-5 estrelas)",
                example = "4.5",
                minimum = "1",
                maximum = "5",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @DecimalMin("1.0") @DecimalMax("5.0")
        Double stars,

        @Schema(description = "Comentário opcional da avaliação",
                example = "Avaliação administrativa")
        String comment
) {}
