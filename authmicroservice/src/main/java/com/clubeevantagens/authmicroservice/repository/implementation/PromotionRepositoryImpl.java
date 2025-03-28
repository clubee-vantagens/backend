package com.clubeevantagens.authmicroservice.repository.implementation;

import com.clubeevantagens.authmicroservice.database.PromotionJPARepository;
import com.clubeevantagens.authmicroservice.database.model.PromotionModel;
import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.dto.GetMostRescuedPromotionsInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PromotionRepositoryImpl implements PromotionRepository {
  private final PromotionJPARepository connection;

  public PromotionRepositoryImpl(PromotionJPARepository connection) {
    this.connection = connection;
  }

  @Override
  public Long save(Promotion promotion) {
    PromotionModel model = new PromotionModel(promotion.getPromotionId(), promotion.getCompanyId(), promotion.getPromotionName(), promotion.getPoints(), promotion.getReviewsRating(), promotion.getTotalReviews(), promotion.getTotalRedemptions(), promotion.getRedemptionsLast7Days(), promotion.getCycleStart(), promotion.getPromotionImage(), promotion.getCreatedAt());
    return this.connection.save(model).getPromotionId();
  }

  @Override
  public List<GetPromotionsCreatedInLast7DaysProjection> findPromotionsCreatedInLast7Days(Long clientId) {
    return connection.findPromotionsCreatedInLast7Days(clientId)
            .stream()
            .map(tuple -> new GetPromotionsCreatedInLast7DaysProjection(
                    tuple.get("promotionId", Long.class),
                    tuple.get("promotionName", String.class),
                    tuple.get("points", Integer.class),
                    tuple.get("reviewsRating", Double.class),
                    tuple.get("totalReviews", Integer.class),
                    tuple.get("promotionImage", String.class)
            ))
            .collect(Collectors.toList());
  }

  @Override
  public List<GetMostRescuedPromotionsInLast7DaysProjection> findMostRescuedPromotionsInLast7Days(Long clientId) {
    return connection.findMostRescuedPromotionsInLast7Days(clientId)
            .stream()
            .map(tuple -> new GetMostRescuedPromotionsInLast7DaysProjection(
                    tuple.get("promotionId", Long.class),
                    tuple.get("promotionName", String.class),
                    tuple.get("points", Integer.class),
                    tuple.get("reviewsRating", Double.class),
                    tuple.get("totalReviews", Integer.class),
                    tuple.get("promotionImage", String.class),
                    tuple.get("isFavorite", Boolean.class)
            ))
            .collect(Collectors.toList());
  }

  @Override
  public Promotion findByPromotionId(Long promotionId) {
    Optional<PromotionModel> data = this.connection.findById(promotionId);
    if(data.isEmpty()) throw new RuntimeException("Promotion not found");
    PromotionModel model = data.get();
    return new Promotion(model.getPromotionId(), model.getCompanyId(), model.getPromotionName(), model.getPoints(), model.getReviewsRating(), model.getTotalReviews(), model.getTotalRedemptions(), model.getRedemptionsLast7Days(), model.getCycleStart(), model.getPromotionImage(), model.getCreatedAt());
  }
}
