package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.Promotion;
import com.clubeevantagens.authmicroservice.app.dto.CreatePromotionInput;
import com.clubeevantagens.authmicroservice.app.repository.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePromotion {
  private final PromotionRepository promotionRepository;

  public CreatePromotion(PromotionRepository promotionRepository) {
      this.promotionRepository = promotionRepository;
  }

  public void execute(CreatePromotionInput input) {
    Promotion promotion = new Promotion(input.companyId(), input.promotionName(), input.points(), input.promotionImage());
    this.promotionRepository.save(promotion);
  }
}
