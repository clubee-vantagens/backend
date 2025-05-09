package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.ReviewPromotion;

import java.util.List;

public interface ReviewPromotionRepository {
  void save(ReviewPromotion reviewPromotion);
  List<ReviewPromotion> findAllByPromotionId(Long promotionId);
}
