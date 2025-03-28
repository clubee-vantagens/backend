package com.clubeevantagens.authmicroservice.model.dto;

public record CreatePromotionInput(Long companyId, String promotionName, Integer points, String promotionImage) {
}
