package com.clubeevantagens.authmicroservice.app.dto;

public record GetPromotionsCreatedInLast7DaysProjection(Long promotionId, String promotionName, String category, Double reviewsRating, Integer totalReviews, String promotionImage, Boolean isFavorite) {
}
