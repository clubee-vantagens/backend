package com.clubeevantagens.authmicroservice.model.dto;

public record CreateReviewInput(Long clientId, Long promotionId, Double stars, String comment) {
}
