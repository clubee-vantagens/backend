package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.Rating;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RatingTest {
  @Test
  void shouldCreateAValidRating() {
    Rating rating = new Rating(5.0, 2);
    assertThat(rating.getReviewsRating()).isEqualTo(5.0);
    assertThat(rating.getTotalReviews()).isEqualTo(2);
  }

  @Test
  void shouldThrowAnErrorIfReviewsRatingIsEqualNull() {
    Exception error = assertThrows(RuntimeException.class, () -> new Rating(null, 2));
    assertThat(error.getMessage()).isEqualTo("Review rating cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfTotalReviewsIsEqualNull() {
    Exception error = assertThrows(RuntimeException.class, () -> new Rating(5.0, null));
    assertThat(error.getMessage()).isEqualTo("Total reviews cannot be null");
  }
}
