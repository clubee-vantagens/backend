package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.FavoritePromotion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FavoritePromotionTest {
  @Test
  void shouldCreateAFavoritePromotion() {
    FavoritePromotion favoritePromotion = new FavoritePromotion(1L, 1L);
    assertThat(favoritePromotion.getFavoritePromotionId()).isNull();
    assertThat(favoritePromotion.getClientId()).isEqualTo(1L);
    assertThat(favoritePromotion.getPromotionId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowAnErrorIfClientIdIsNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new FavoritePromotion(null, 1L));
    assertThat(error.getMessage()).isEqualTo("Client identifier cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfPromotionIdIsNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new FavoritePromotion(1L, null));
    assertThat(error.getMessage()).isEqualTo("Promotion identifier cannot be null");
  }
}
