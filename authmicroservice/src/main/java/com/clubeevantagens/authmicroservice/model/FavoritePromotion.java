package com.clubeevantagens.authmicroservice.model;

public class FavoritePromotion {
  private Long favoritePromotionId;
  private final Long clientId;
  private final Long promotionId;

  public FavoritePromotion(Long clientId, Long promotionId) {
    if(clientId == null) throw new RuntimeException("Client identifier cannot be null");
    if(promotionId == null) throw new RuntimeException("Promotion identifier cannot be null");
    this.clientId = clientId;
    this.promotionId = promotionId;
  }

  public Long getFavoritePromotionId() {
    return this.favoritePromotionId;
  }

  public Long getClientId() {
    return this.clientId;
  }

  public Long getPromotionId() {
    return this.promotionId;
  }
}
