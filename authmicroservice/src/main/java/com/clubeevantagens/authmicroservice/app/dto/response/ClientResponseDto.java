package com.clubeevantagens.authmicroservice.app.dto.response;

import com.clubeevantagens.authmicroservice.domain.entity.Address;
import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.entity.Client;
import com.clubeevantagens.authmicroservice.domain.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Schema(name = "ClientResponse", description = "DTO de resposta com dados do cliente")
public record ClientResponseDto(
        @Schema(example = "João da Silva") String name,
        @Schema(example = "123.456.789-09") String cpf,
        @Schema(example = "(11)91234-5678") String phoneNumber,
        @Schema(example = "true") Boolean termsOfUse,

        @Schema(description = "Data/hora da aceitação dos termos",
                example = "2023-08-25 14:30:45.123456")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime dateTermsOfUse,

        @Schema(example = "Joana Silva") String socialName,

        @Schema(implementation = Address.class) Address address,

        @Schema(description = "Lista de avaliações do cliente")
        List<Review> reviews,

        @Schema(example = "[\"PAPELARIA\", \"PETSHOP\", \"ALIMENTAÇÃO\"]")
        Set<Category> preferences,

        @Schema(example = "15/05/1990", format = "dd/MM/yyyy")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthDate,

        @Schema(example = "https://example.com/photo.jpg")
        String photo
) {
    public ClientResponseDto(Client client){
        this(
                client.getName(),
                client.getCpf(),
                client.getPhoneNumber(),
                client.getTermsOfUse(),
                client.getDateTermsOfUse(),
                client.getSocialName(),
                client.getAddress(),
                client.getReviews(),
                client.getPreferences(),
                client.getBirthDate(),
                client.getPhoto()
        );
    }
}
