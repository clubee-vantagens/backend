package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.FavoritePromotion;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.repository.FavoritePromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateFavoritePromotion {
  private final FavoritePromotionRepository favoritePromotionRepository;

  public CreateFavoritePromotion(FavoritePromotionRepository favoritePromotionRepository) {
    this.favoritePromotionRepository = favoritePromotionRepository;
  }

  public void execute(CreateFavoritePromotionInput input) {
    FavoritePromotion favoritePromotion = new FavoritePromotion(input.clientId(), input.promotionId());
    this.favoritePromotionRepository.save(favoritePromotion);
  }
}
