package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.app.dto.GetTotalFavoritesInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoritePromotionRepository;
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
