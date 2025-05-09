package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.FavoritePromotion;

public interface FavoritePromotionRepository {
  Long save(FavoritePromotion favoritePromotion);
  int getTotalFavorites(Long clientId);
}
