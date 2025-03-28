package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import com.clubeevantagens.authmicroservice.model.dto.CreateReviewInput;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.repository.ReviewPromotionRepository;
import com.clubeevantagens.authmicroservice.model.RatingCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateReview {
  private final ReviewPromotionRepository reviewPromotionRepository;
  private final PromotionRepository promotionRepository;

  public CreateReview(ReviewPromotionRepository reviewPromotionRepository, PromotionRepository promotionRepository) {
    this.reviewPromotionRepository = reviewPromotionRepository;
    this.promotionRepository = promotionRepository;
  }

  @Transactional
  public void execute(CreateReviewInput input) {
    ReviewPromotion review = new ReviewPromotion(input.clientId(), input.promotionId(), input.stars(), input.comment());
    this.reviewPromotionRepository.save(review);
    List<ReviewPromotion> reviews = this.reviewPromotionRepository.findAllByPromotionId(input.promotionId());
    int totalReviews = reviews.size();
    double reviewsRating = RatingCalculator.calculate(reviews);
    Promotion promotion = this.promotionRepository.findByPromotionId(input.promotionId());
    promotion.setRating(reviewsRating, totalReviews);
    this.promotionRepository.save(promotion);
  }
}
