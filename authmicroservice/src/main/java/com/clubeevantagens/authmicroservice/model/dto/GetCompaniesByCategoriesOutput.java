package com.clubeevantagens.authmicroservice.model.dto;

public record GetCompaniesByCategoriesOutput(Long companyId, String companyName, String category, double distance, double reviewsRating, int totalReviews, String companyProfile, boolean isFavorite) {
}
