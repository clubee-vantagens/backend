package com.clubeevantagens.authmicroservice.model;

public class Rating {
  private Double reviewsRating;
  private Integer totalReviews;

  public Rating(Double reviewsRating, Integer totalReviews) {
    if(reviewsRating == null) throw new RuntimeException("Review rating cannot be null");
    if(totalReviews == null) throw new RuntimeException("Total reviews cannot be null");
    if(reviewsRating < 0 || reviewsRating > 5) throw new RuntimeException("Invalid review rating");
    this.reviewsRating = reviewsRating;
    this.totalReviews = totalReviews;
  }

  public Double getReviewsRating() {
    return this.reviewsRating;
  }

  public Integer getTotalReviews() {
    return this.totalReviews;
  }
}
