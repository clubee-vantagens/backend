package com.clubeevantagens.authmicroservice.model.dto;

public record GetMostRescuedPromotionsInLast7DaysProjection(Long promotionId, String promotionName, String category, Double reviewsRating, Integer totalReviews, String promotionImage, Boolean isFavorite) {
}
