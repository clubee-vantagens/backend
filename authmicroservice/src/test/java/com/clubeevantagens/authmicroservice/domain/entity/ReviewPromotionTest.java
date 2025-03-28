package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewPromotionTest {

  @Test
  void shouldCreateReviewWithMinimumParameters() {
    ReviewPromotion review = new ReviewPromotion(1L, 1L, 5.0, "Very good meal");
    assertThat(review.getReviewId()).isNull();
    assertThat(review.getClientId()).isEqualTo(1L);
    assertThat(review.getPromotionId()).isEqualTo(1L);
    assertThat(review.getStars()).isEqualTo(5.0);
    assertThat(review.getComment()).isEqualTo("Very good meal");
    assertThat(review.getCreatedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  void shouldCreateReviewWithMaximumParameters() {
    LocalDateTime createdAt = LocalDateTime.now();
    ReviewPromotion review = new ReviewPromotion(1L, 1L, 1L, 5.0, "Very good meal", createdAt);
    assertThat(review.getReviewId()).isEqualTo(1L);
    assertThat(review.getClientId()).isEqualTo(1L);
    assertThat(review.getPromotionId()).isEqualTo(1L);
    assertThat(review.getStars()).isEqualTo(5.0);
    assertThat(review.getComment()).isEqualTo("Very good meal");
    assertThat(review.getCreatedAt()).isEqualTo(createdAt);
  }

  @Test
  void shouldThrowAnErrorIfClientIdIsEqualNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new ReviewPromotion(null, 1L, 5.0, "Very good meal"));
    assertThat(error.getMessage()).isEqualTo("Client identifier cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfCompanyIdIsEqualNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new ReviewPromotion(1L, null, 5.0, "Very good meal"));
    assertThat(error.getMessage()).isEqualTo("Promotion identifier cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfStarsIsSmallerThanZero() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new ReviewPromotion(1L, 1L, -1.0, "Very good meal"));
    assertThat(error.getMessage()).isEqualTo("Invalid stars");
  }

  @Test
  void shouldThrowAnErrorIfStarsIsGreaterThanFive() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new ReviewPromotion(1L, 1L, 6.0, "Very good meal"));
    assertThat(error.getMessage()).isEqualTo("Invalid stars");
  }
}
