package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysInput;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysOutput;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPromotionsCreatedInLast7Days {
  private final PromotionRepository promotionRepository;

  public GetPromotionsCreatedInLast7Days(PromotionRepository promotionRepository) {
    this.promotionRepository = promotionRepository;
  }

  public List<GetPromotionsCreatedInLast7DaysOutput> execute(GetPromotionsCreatedInLast7DaysInput input) {
    List<GetPromotionsCreatedInLast7DaysProjection> promotionsProjection = this.promotionRepository.findPromotionsCreatedInLast7Days(input.clientId());

    List<GetPromotionsCreatedInLast7DaysOutput> promotionsCreatedInLast7Days = new ArrayList<>();

    for(GetPromotionsCreatedInLast7DaysProjection projection : promotionsProjection) {
      promotionsCreatedInLast7Days.add(new GetPromotionsCreatedInLast7DaysOutput(
              projection.promotionId(),
              projection.promotionName(),
              1.5,
              projection.reviewsRating(),
              projection.totalReviews(),
              projection.points(),
              projection.promotionImage(),
              "company-profile-mock-url"
      ));
    }

    return promotionsCreatedInLast7Days;
  }
}
