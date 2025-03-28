package com.clubeevantagens.authmicroservice.model;

import java.time.LocalDateTime;

public class Promotion {
  private Long promotionId;
  private final Long companyId;
  private String promotionName;
  private int points;
  private Rating rating;
  private RedemptionTracker redemptionTracker;
  private String promotionImage;
  private LocalDateTime createdAt;

  public Promotion(Long companyId, String promotionName, Integer points, String promotionImage) {
    if(companyId == null) throw new RuntimeException("Company identifier cannot be null");
    if(promotionName == null) throw new RuntimeException("Promotion name cannot be null");
    if(promotionName.isEmpty()) throw new RuntimeException("Promotion name cannot be empty");
    if(points < 0) throw new RuntimeException("Invalid points");
    this.companyId = companyId;
    this.promotionName = promotionName;
    this.points = points;
    this.rating = new Rating(5.0, 0);
    this.redemptionTracker = new RedemptionTracker(0, 0);
    this.promotionImage = promotionImage;
    this.createdAt = LocalDateTime.now();
  }

  public Promotion(Long promotionId, Long companyId, String promotionName, Integer points, Double reviewsRating, Integer totalReviews, int totalRedemptions, int redemptionsLast7Days, LocalDateTime cycleStart, String promotionImage, LocalDateTime createdAt) {
    if(companyId == null) throw new RuntimeException("Company identifier cannot be null");
    if(promotionName == null) throw new RuntimeException("Promotion name cannot be null");
    if(points < 0) throw new RuntimeException("Invalid points");
    this.promotionId = promotionId;
    this.companyId = companyId;
    this.promotionName = promotionName;
    this.points = points;
    this.rating = new Rating(reviewsRating, totalReviews);
    this.redemptionTracker = new RedemptionTracker(totalRedemptions, redemptionsLast7Days, cycleStart);
    this.promotionImage = promotionImage;
    this.createdAt = createdAt;
  }

  public Long getPromotionId() {
    return this.promotionId;
  }

  public Long getCompanyId() {
    return this.companyId;
  }

  public String getPromotionName() {
    return this.promotionName;
  }

  public Integer getPoints() {
    return this.points;
  }

  public Double getReviewsRating() {
    return this.rating.getReviewsRating();
  }

  public Integer getTotalReviews() {
    return this.rating.getTotalReviews();
  }

  public void setRating(Double reviewsRating, Integer totalReviews) {
    this.rating = new Rating(reviewsRating, totalReviews);
  }

  public void registerRedemption() {
    this.redemptionTracker.registerRedemption();
  }

  public int getTotalRedemptions() {
    return this.redemptionTracker.getTotalRedemptions();
  }

  public int getRedemptionsLast7Days() {
    return this.redemptionTracker.getRedemptionsLast7Days();
  }

  public LocalDateTime getCycleStart() {
    return this.redemptionTracker.getCycleStart();
  }

  public String getPromotionImage() {
    return this.promotionImage;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}
