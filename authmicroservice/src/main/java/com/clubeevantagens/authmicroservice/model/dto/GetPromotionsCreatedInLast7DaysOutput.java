package com.clubeevantagens.authmicroservice.model.dto;

public record GetPromotionsCreatedInLast7DaysOutput(Long promotionId, String promotionName, Double distance, Double reviewsRating, Integer totalReviews, Integer points, String promotionImage, String companyProfile) {
}