package com.clubeevantagens.authmicroservice.model;

import java.time.LocalDateTime;

public class ReviewPromotion {
  private Long reviewId;
  private final Long clientId;
  private final Long promotionId;
  private Double stars;
  private String comment;
  private final LocalDateTime createdAt;

  public ReviewPromotion(Long clientId, Long promotionId, Double stars, String comment) {
    if(clientId == null) throw new RuntimeException("Client identifier cannot be null");
    if(promotionId == null) throw new RuntimeException("Promotion identifier cannot be null");
    if(stars < 0 || stars > 5) throw new RuntimeException("Invalid stars");
    this.clientId = clientId;
    this.promotionId = promotionId;
    this.stars = stars;
    this.comment = comment;
    this.createdAt = LocalDateTime.now();
  }

  public ReviewPromotion(Long reviewId, Long clientId, Long promotionId, Double stars, String comment, LocalDateTime createdAt) {
    if(clientId == null) throw new RuntimeException("Client identifier cannot be null");
    if(promotionId == null) throw new RuntimeException("Promotion identifier cannot be null");
    if(stars < 0 || stars > 5) throw new RuntimeException("Invalid stars");
    this.reviewId = reviewId;
    this.clientId = clientId;
    this.promotionId = promotionId;
    this.stars = stars;
    this.comment = comment;
    this.createdAt = createdAt;
  }

  public Long getReviewId() {
    return this.reviewId;
  }

  public Long getClientId() {
    return this.clientId;
  }

  public Long getPromotionId() {
    return this.promotionId;
  }

  public Double getStars() {
    return this.stars;
  }

  public String getComment() {
    return this.comment;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}
