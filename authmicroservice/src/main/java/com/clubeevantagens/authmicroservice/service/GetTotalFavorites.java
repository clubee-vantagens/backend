package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.dto.GetTotalFavoritesInput;
import com.clubeevantagens.authmicroservice.repository.FavoritePromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTotalFavorites {
  private final FavoritePromotionRepository favoritePromotionRepository;

  public GetTotalFavorites(FavoritePromotionRepository favoritePromotionRepository) {
    this.favoritePromotionRepository = favoritePromotionRepository;
  }

  public int execute(GetTotalFavoritesInput input) {
    return this.favoritePromotionRepository.getTotalFavorites(input.clientId());
  }
}
