package com.clubeevantagens.authmicroservice.model.dto;

public record GetCompaniesByCategoriesProjection(Long companyId, String companyName, String category, Boolean isFavorite) {
}
