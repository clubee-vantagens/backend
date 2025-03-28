package com.clubeevantagens.authmicroservice.database.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions", schema = "public")
public class PromotionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "promotion_id", nullable = false, unique = true)
  private Long promotionId;
  @Column(name = "company_id", nullable = false)
  private Long companyId;
  @Column(name = "promotion_name", nullable = false)
  private String promotionName;
  @Column(name = "points", nullable = false)
  private int points;
  @Column(name = "reviews_rating", nullable = false)
  private Double reviewsRating;
  @Column(name = "total_reviews", nullable = false)
  private Integer totalReviews;
  @Column(name = "total_redemptions", nullable = false)
  private Integer totalRedemptions;
  @Column(name = "redemptions_last_7_days", nullable = false)
  private Integer redemptionsLast7Days;
  @Column(name = "cycle_start")
  private LocalDateTime cycleStart;
  @Column(name = "promotion_image", nullable = false)
  private String promotionImage;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public PromotionModel() {}

  public PromotionModel(Long promotionId, Long companyId, String promotionName, int points, Double reviewsRating, Integer totalReviews, int totalRedemptions, int redemptionsLast7Days, LocalDateTime cycleStart, String promotionImage, LocalDateTime createdAt) {
    this.promotionId = promotionId;
    this.companyId = companyId;
    this.promotionName = promotionName;
    this.points = points;
    this.reviewsRating = reviewsRating;
    this.totalReviews = totalReviews;
    this.totalRedemptions = totalRedemptions;
    this.redemptionsLast7Days = redemptionsLast7Days;
    this.cycleStart = cycleStart;
    this.promotionImage = promotionImage;
    this.createdAt = createdAt;
  }

  public Long getPromotionId() {
    return this.promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public Long getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public String getPromotionName() {
    return this.promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public int getPoints() {
    return this.points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public Double getReviewsRating() {
    return this.reviewsRating;
  }

  public void setReviewsRating(Double reviewsRating) {
    this.reviewsRating = reviewsRating;
  }

  public Integer getTotalReviews() {
    return this.totalReviews;
  }

  public void setTotalReviews(Integer totalReviews) {
    this.totalReviews = totalReviews;
  }

  public int getTotalRedemptions() {
    return this.totalRedemptions;
  }

  public void setTotalRedemptions(int totalRedemptions) {
    this.totalRedemptions = totalRedemptions;
  }

  public int getRedemptionsLast7Days() {
    return this.redemptionsLast7Days;
  }

  public void setRedemptionsLast7Days(int redemptionsLast7Days) {
    this.redemptionsLast7Days = redemptionsLast7Days;
  }

  public LocalDateTime getCycleStart() {
    return this.cycleStart;
  }

  public void setCycleStart(LocalDateTime cycleStart) {
    this.cycleStart = cycleStart;
  }

  public String getPromotionImage() {
    return this.promotionImage;
  }

  public void setPromotionImage(String promotionImage) {
    this.promotionImage = promotionImage;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
