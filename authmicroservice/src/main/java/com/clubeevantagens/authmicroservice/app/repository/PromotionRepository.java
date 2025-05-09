package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.Promotion;
import com.clubeevantagens.authmicroservice.app.dto.GetMostRescuedPromotionsInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.app.dto.GetPromotionsCreatedInLast7DaysProjection;

import java.util.List;

public interface PromotionRepository {
  Long save(Promotion promotion);
  List<GetPromotionsCreatedInLast7DaysProjection> findPromotionsCreatedInLast7Days(Long clientId);
  List<GetMostRescuedPromotionsInLast7DaysProjection> findMostRescuedPromotionsInLast7Days(Long clientId);
  Promotion findByPromotionId(Long promotionId);
  List<Promotion> findAllByCompanyId(Long companyId);
}
