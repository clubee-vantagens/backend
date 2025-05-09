package com.clubeevantagens.authmicroservice.app.dto;

public record CreatePromotionInput(Long companyId, String promotionName, Integer points, String promotionImage) {
}
