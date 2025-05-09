package com.clubeevantagens.authmicroservice.app.dto;

public record GetCompaniesByCategoriesProjection(Long companyId, String companyName, String category, Boolean isFavorite) {
}
