package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.FavoritePromotion;
import com.clubeevantagens.authmicroservice.app.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoritePromotionRepository;
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
