package com.clubeevantagens.authmicroservice.app.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CompanyUpdateRequestDto(
    @Schema(description = "Razão social da empresa",
            example = "Tech Solutions LTDA")
    String companyName,

    @Schema(description = "CNPJ válido",
            example = "12.345.678/0001-95")
    String cnpj,

    @Schema(description = "Telefone no formato (99)99999-9999",
            example = "(11)91234-5678",
            pattern = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank @Pattern(regexp = "...") String phoneNumber,

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
    @NotBlank String complement,

    @Schema(description = "Categoria principal da empresa",
            example = "Alimentação")
    String categoryName
) {
}
