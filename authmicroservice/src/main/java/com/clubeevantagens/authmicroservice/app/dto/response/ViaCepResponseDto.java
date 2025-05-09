package com.clubeevantagens.authmicroservice.app.dto.response;

import com.clubeevantagens.authmicroservice.domain.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ViaCepResponse", description = "DTO de resposta da API ViaCEP (formato original)")
public record ViaCepResponseDto(
        @Schema(description = "CEP no formato da API ViaCEP", example = "01311-000")
        @NotBlank @JsonProperty("cep") String cep,

        @Schema(description = "Nome do logradouro", example = "Avenida Paulista")
        @NotBlank @JsonProperty("logradouro") String street,

        @Schema(description = "Bairro", example = "Bela Vista")
        @NotBlank @JsonProperty("bairro") String district,

        @Schema(description = "Cidade", example = "São Paulo")
        @NotBlank @JsonProperty("localidade") String city,

        @Schema(description = "Sigla do estado", example = "SP")
        @NotBlank @JsonProperty("uf") String state
) {
    public ViaCepResponseDto(Address address){
        this(
                address.getCep(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity(),
                String.valueOf(address.getStates())
        );
    }
}