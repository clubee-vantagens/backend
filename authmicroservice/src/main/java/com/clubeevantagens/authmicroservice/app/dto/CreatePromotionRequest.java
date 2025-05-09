package com.clubeevantagens.authmicroservice.app.dto;

public record CreatePromotionRequest(String promotionName, Integer points, String promotionImage) {
}
