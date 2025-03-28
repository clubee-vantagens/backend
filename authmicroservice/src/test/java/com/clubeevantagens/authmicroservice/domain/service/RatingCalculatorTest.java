package com.clubeevantagens.authmicroservice.domain.service;

import com.clubeevantagens.authmicroservice.model.RatingCalculator;
import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RatingCalculatorTest {
  @Test
  void shouldReturnZeroWhenListIsEmpty() {
    List<ReviewPromotion> reviews = Collections.emptyList();
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(0.0);
  }

  @Test
  void shouldReturnZeroWhenListIsNull() {
    Double result = RatingCalculator.calculate(null);
    assertThat(result).isEqualTo(0.0);
  }

  @Test
  void shouldReturnRatingWhenOnlyOneReviewExists() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 4.0, "Good"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(4.0);
  }

  @Test
  void shouldCalculateWeightedAverageForMultipleEqualReviews() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(5.0);
  }

  @Test
  void shouldCalculateWeightedAverageForDifferentRatings() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 4.0,"Good"));
    reviews.add(new ReviewPromotion(1L, 1L, 3.0,"Not bad"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(4.0);
  }

  @Test
  void shouldCalculateAverageForExtremeValues() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 0.0,"Very bad"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(2.5);
  }

  @Test
  void shouldCalculateAverageForDecimalRatings() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 3.5,"Good"));
    reviews.add(new ReviewPromotion(1L, 1L, 4.5,"Nice"));
    reviews.add(new ReviewPromotion(1L, 1L, 2.5,"Not bad"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(3.5);
  }

  @Test
  void shouldCalculateAverageForLargeNumberOfReviews() {
    List<ReviewPromotion> reviews = Collections.nCopies(1000, new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(5.0);
  }

  @Test
  void shouldRoundDownWhenDecimalIsBelowPointFive() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Nice"));
    reviews.add(new ReviewPromotion(1L, 1L, 2.5,"Bad"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(4.2);
  }

  @Test
  void shouldRoundUpWhenDecimalIsAbovePointFive() {
    List<ReviewPromotion> reviews = new ArrayList<>();
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Very good"));
    reviews.add(new ReviewPromotion(1L, 1L, 5.0,"Nice"));
    reviews.add(new ReviewPromotion(1L, 1L, 1.5,"Very bad"));
    Double result = RatingCalculator.calculate(reviews);
    assertThat(result).isEqualTo(3.8);
  }

}
