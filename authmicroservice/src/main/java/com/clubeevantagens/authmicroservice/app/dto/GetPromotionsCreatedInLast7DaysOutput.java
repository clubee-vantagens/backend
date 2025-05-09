package com.clubeevantagens.authmicroservice.app.dto;

public record GetPromotionsCreatedInLast7DaysOutput(Long promotionId, String promotionName, Double distance, Double reviewsRating, Integer totalReviews, String category, String promotionImage, String companyProfile, Boolean isFavorite) {
}