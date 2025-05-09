package com.clubeevantagens.authmicroservice.app.dto;

public record CreateReviewInput(Long clientId, Long promotionId, Double stars, String comment) {
}
