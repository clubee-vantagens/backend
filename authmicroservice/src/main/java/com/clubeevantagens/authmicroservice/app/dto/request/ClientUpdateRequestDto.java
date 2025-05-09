package com.clubeevantagens.authmicroservice.app.dto.request;

import com.clubeevantagens.authmicroservice.app.validator.validCategories.ValidCategories;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

@Schema(name = "UpdateClientRequest", description = "DTO para atualização de dados do cliente")
public record ClientUpdateRequestDto(
        @Schema(description = "Nome completo do cliente",
                example = "João da Silva",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name,

        @Schema(description = "CPF válido",
                example = "123.456.789-09",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @CPF @NotBlank String cpf,

        @Schema(description = "Telefone no formato (99)99999-9999",
                example = "(11)91234-5678",
                pattern = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String phoneNumber,

        @Schema(description = "Nome social (opcional)",
                example = "Joana Silva")
        @Nullable String socialName,

        @Schema(description = "Categorias de interesse (mínimo 3 ou nenhuma)",
                example = "Alimentação",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidCategories
        Set<String> preferences,

        @Schema(description = "Data de nascimento (dd/MM/yyyy)",
                example = "15/05/1990",
                format = "dd/MM/yyyy")
        @JsonFormat(pattern = "dd/MM/yyyy") @Past LocalDate birthDate,

        @Schema(description = "URL da foto do perfil",
                example = "https://example.com/photo.jpg")
        String photo,

        @Schema(description = "CEP no formato 99999-999",
                example = "01311-000",
                pattern = "^\\d{5}-\\d{3}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String cep,

        @Schema(description = "Número do endereço",
                example = "150",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive Integer addressNumber,

        @Schema(description = "Complemento do endereço",
                example = "Apto 42",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String complement
) {
}
