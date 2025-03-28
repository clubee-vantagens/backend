package com.clubeevantagens.authmicroservice.model.dto;

public record CreatePromotionRequest(String promotionName, Integer points, String promotionImage) {
}
