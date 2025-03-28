package com.clubeevantagens.authmicroservice.database.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews", schema = "public")
public class ReviewPromotionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id", nullable = false, unique = true)
  private Long reviewId;
  @Column(name = "client_id", nullable = false)
  private Long clientId;
  @Column(name = "promotion_id", nullable = false)
  private Long promotionId;
  @Column(name = "stars", nullable = false)
  private Double stars;
  @Column(name = "comment", nullable = true)
  private String comment;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public ReviewPromotionModel() {}

  public ReviewPromotionModel(Long reviewId, Long clientId, Long promotionId, Double stars, String comment, LocalDateTime createdAt) {
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

  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  public Long getClientId() {
    return this.clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getPromotionId() {
    return this.promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public Double getStars() {
    return this.stars;
  }

  public void setStars(Double stars) {
    this.stars = stars;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
