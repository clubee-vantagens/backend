package com.clubeevantagens.authmicroservice.model.dto;

public record GetPromotionsCreatedInLast7DaysProjection(Long promotionId, String promotionName, Integer points, Double reviewsRating, Integer totalReviews, String promotionImage) {
}
