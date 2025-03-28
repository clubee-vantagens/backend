package com.clubeevantagens.authmicroservice.repository.implementation;

import com.clubeevantagens.authmicroservice.database.ReviewPromotionJPARepository;
import com.clubeevantagens.authmicroservice.database.model.ReviewPromotionModel;
import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import com.clubeevantagens.authmicroservice.repository.ReviewPromotionRepository;
import com.clubeevantagens.authmicroservice.model.Reviews;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewPromotionRepositoryImpl implements ReviewPromotionRepository {
  private final ReviewPromotionJPARepository connection;

  public ReviewPromotionRepositoryImpl(ReviewPromotionJPARepository connection) {
    this.connection = connection;
  }

  @Override
  public void save(ReviewPromotion reviewPromotion) {
    ReviewPromotionModel model = new ReviewPromotionModel(reviewPromotion.getReviewId(), reviewPromotion.getClientId(), reviewPromotion.getPromotionId(), reviewPromotion.getStars(), reviewPromotion.getComment(), reviewPromotion.getCreatedAt());
    this.connection.save(model);
  }

  @Override
  public List<ReviewPromotion> findAllByPromotionId(Long promotionId) {
    return connection.findAllByPromotionId(promotionId)
            .stream()
            .map(model -> new ReviewPromotion(
                    model.getReviewId(),
                    model.getClientId(),
                    model.getPromotionId(),
                    model.getStars(),
                    model.getComment(),
                    model.getCreatedAt()
            )).collect(Collectors.toList());
  }
}
