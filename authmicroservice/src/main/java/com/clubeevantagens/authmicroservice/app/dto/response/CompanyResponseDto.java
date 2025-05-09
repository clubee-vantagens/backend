package com.clubeevantagens.authmicroservice.app.dto.response;

import com.clubeevantagens.authmicroservice.domain.entity.Address;
import com.clubeevantagens.authmicroservice.domain.entity.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CreateCompanyRequest", description = "DTO para cadastro de empresa")
public record CompanyResponseDto(
        @Schema(description = "Razão social da empresa",
                example = "Tech Solutions LTDA")
        String companyName,

        @Schema(description = "CNPJ válido",
                example = "12.345.678/0001-95")
        String cnpj,

        @Schema(description = "Telefone comercial",
                example = "(11)4004-4004")
        @NotBlank String phoneNumber,

        @Schema(description = "Categoria principal da empresa",
                example = "Alimentação")
        String categoryName,

        @Schema(description = "Aceite dos termos de uso",
                example = "true")
        boolean termsOfUse,

        @Schema(implementation = Address.class) Address address
) {
        public CompanyResponseDto(Company company){
                this(
                        company.getCompanyName(),
                        company.getCnpj(),
                        company.getPhoneNumber(),
                        company.getCategory().getName().getDisplayName(),
                        company.isTermsOfUse(),
                        company.getAddress()
                );
        }
}
