package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.ReviewPromotion;

import java.util.List;

public interface ReviewPromotionRepository {
  void save(ReviewPromotion reviewPromotion);
  List<ReviewPromotion> findAllByPromotionId(Long promotionId);
}
