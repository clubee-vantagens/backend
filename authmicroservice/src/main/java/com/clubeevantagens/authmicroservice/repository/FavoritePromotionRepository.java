package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.FavoritePromotion;

public interface FavoritePromotionRepository {
  Long save(FavoritePromotion favoritePromotion);
  int getTotalFavorites(Long clientId);
}
