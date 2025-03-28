package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.dto.GetMostRescuedPromotionsInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysProjection;

import java.util.List;

public interface PromotionRepository {
  Long save(Promotion promotion);
  List<GetPromotionsCreatedInLast7DaysProjection> findPromotionsCreatedInLast7Days(Long clientId);
  List<GetMostRescuedPromotionsInLast7DaysProjection> findMostRescuedPromotionsInLast7Days(Long clientId);
  Promotion findByPromotionId(Long promotionId);
}
