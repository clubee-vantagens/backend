package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.Promotion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PromotionTest {
  @Test
  void shouldCreateAnInstanceWithAMinimumOfParameters() {
    Promotion promotion = new Promotion(1L, "Pet Shop 2 em 1", 40, "url-image");
    assertThat(promotion).isInstanceOf(Promotion.class);
    assertThat(promotion.getPromotionId()).isNull();
    assertThat(promotion.getCompanyId()).isEqualTo(1L);
    assertThat(promotion.getPromotionName()).isEqualTo("Pet Shop 2 em 1");
    assertThat(promotion.getPoints()).isEqualTo(40);
    assertThat(promotion.getTotalRedemptions()).isEqualTo(0);
    assertThat(promotion.getRedemptionsLast7Days()).isEqualTo(0);
    assertThat(promotion.getCycleStart()).isInstanceOf(LocalDateTime.class);
    assertThat(promotion.getPromotionImage()).isEqualTo("url-image");
    assertThat(promotion.getCreatedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  void shouldCreateAnInstanceWithAMaximumOfParameters() {
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime cycleStart = LocalDateTime.now();
    Promotion promotion = new Promotion(1L, 1L, "Pet Shop 2 em 1", 40, 5.0, 0, 0, 0, cycleStart,"url-image", createdAt);
    assertThat(promotion).isInstanceOf(Promotion.class);
    assertThat(promotion.getPromotionId()).isEqualTo(1L);
    assertThat(promotion.getCompanyId()).isEqualTo(1L);
    assertThat(promotion.getPromotionName()).isEqualTo("Pet Shop 2 em 1");
    assertThat(promotion.getPoints()).isEqualTo(40);
    assertThat(promotion.getReviewsRating()).isEqualTo(5.0);
    assertThat(promotion.getTotalReviews()).isEqualTo(0);
    assertThat(promotion.getTotalRedemptions()).isEqualTo(0);
    assertThat(promotion.getRedemptionsLast7Days()).isEqualTo(0);
    assertThat(promotion.getCycleStart()).isEqualTo(cycleStart);;
    assertThat(promotion.getPromotionImage()).isEqualTo("url-image");
    assertThat(promotion.getCreatedAt()).isEqualTo(createdAt);
  }

  @Test
  void shouldRegisterARedemption() {
    Promotion promotion = new Promotion(1L, "Pet Shop 2 em 1", 40, "url-image");
    promotion.registerRedemption();
    assertThat(promotion.getTotalRedemptions()).isEqualTo(1);
    assertThat(promotion.getRedemptionsLast7Days()).isEqualTo(1);
  }

  @Test
  void shouldThrowAnErrorIfCompanyIdIsEqualNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new Promotion(null, "Pet Shop 2 em 1", 40, "url-image"));
    assertThat(error.getMessage()).isEqualTo("Company identifier cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfPromotionNameIsEqualNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new Promotion(1L, null, 40, "url-image"));
    assertThat(error.getMessage()).isEqualTo("Promotion name cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfPromotionNameIsEmpty() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new Promotion(1L, "", 40, "url-image"));
    assertThat(error.getMessage()).isEqualTo("Promotion name cannot be empty");
  }

  @Test
  void shouldThrowAnErrorIfPointsSmallerThanZero() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new Promotion(1L, "Pet Shop 2 em 1", -1, "url-image"));
    assertThat(error.getMessage()).isEqualTo("Invalid points");
  }
}
