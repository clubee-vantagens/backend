package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.dto.CreatePromotionInput;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
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
