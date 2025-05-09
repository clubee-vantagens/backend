package com.clubeevantagens.authmicroservice.app.dto.request;

import com.clubeevantagens.authmicroservice.app.validator.validSingleCategory.ValidSingleCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;

@Schema(name = "CreateCompanyRequest", description = "DTO para cadastro de empresa")
public record CompanyRequestDto(
        @Schema(description = "E-mail corporativo",
                example = "empresa@example.com",
                format = "email")
        @Email String email,

        @Schema(description = "Senha segura (mesmas regras do cliente)",
                example = "SenhaEmpresa@123",
                pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$")
        @Pattern(regexp = "...") String password,

        @Schema(description = "Razão social da empresa",
                example = "Tech Solutions LTDA",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String companyName,

        @Schema(description = "CNPJ válido",
                example = "12.345.678/0001-95",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @CNPJ String cnpj,

        @Schema(description = "Telefone comercial",
                example = "(11)4004-4004",
                pattern = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String contactPhone,

        @Schema(description = "Categoria principal da empresa",
                example = "Alimentação",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidSingleCategory
        String categoryName,

        @Schema(description = "Aceite dos termos de uso",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED)
        boolean termsOfUse,

        @Schema(description = "CEP no formato 99999-999",
                example = "01311-000",
                pattern = "^\\d{5}-\\d{3}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String cep,

        @Schema(description = "Telefone no formato (99)99999-9999",
                example = "(11)91234-5678",
                pattern = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String phoneNumber,

        @Schema(description = "Número do endereço",
                example = "150",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive Integer addressNumber,

        @Schema(description = "Complemento do endereço",
                example = "Apto 42",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String complement
) {}
