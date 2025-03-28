package com.clubeevantagens.authmicroservice.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_promotions", schema = "public")
public class FavoritePromotionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favorite_promotion_id", nullable = false, unique = true)
  private Long favoritePromotionId;
  @Column(name = "client_id", nullable = false)
  private Long clientId;
  @Column(name = "promotion_id", nullable = false)
  private Long promotionId;

  public FavoritePromotionModel() {}

  public FavoritePromotionModel(Long favoritePromotionId, Long clientId, Long promotionId) {
    this.favoritePromotionId = favoritePromotionId;
    this.clientId = clientId;
    this.promotionId = promotionId;
  }

  public Long getFavoritePromotionId() {
    return this.favoritePromotionId;
  }

  public void setFavoritePromotionId(Long favoritePromotionId) {
    this.favoritePromotionId = favoritePromotionId;
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
}
