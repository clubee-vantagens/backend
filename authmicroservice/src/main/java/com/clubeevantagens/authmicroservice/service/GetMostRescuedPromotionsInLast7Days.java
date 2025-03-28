package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.dto.*;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetMostRescuedPromotionsInLast7Days {
  private final PromotionRepository promotionRepository;

  public GetMostRescuedPromotionsInLast7Days(PromotionRepository promotionRepository) {
    this.promotionRepository = promotionRepository;
  }

  public List<GetMostRescuedPromotionsInLast7DaysOutput> execute(GetMostRescuedPromotionsInLast7DaysInput input) {
    List<GetMostRescuedPromotionsInLast7DaysProjection> promotionsProjection = this.promotionRepository.findMostRescuedPromotionsInLast7Days(input.clientId());
    List<GetMostRescuedPromotionsInLast7DaysOutput> mostRescuedPromotionsInLast7Days = new ArrayList<>();
    for(GetMostRescuedPromotionsInLast7DaysProjection projection : promotionsProjection) {
      mostRescuedPromotionsInLast7Days.add(new GetMostRescuedPromotionsInLast7DaysOutput(
              projection.promotionId(),
              projection.promotionName(),
              1.5,
              projection.reviewsRating(),
              projection.totalReviews(),
              projection.points(),
              projection.promotionImage(),
              "company-profile-mock-url",
              projection.isFavorite()
      ));
    }
    return mostRescuedPromotionsInLast7Days;
  }
}
