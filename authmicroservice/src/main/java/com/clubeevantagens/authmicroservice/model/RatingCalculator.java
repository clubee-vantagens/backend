package com.clubeevantagens.authmicroservice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingCalculator {
  public static double calculate(List<ReviewPromotion> reviews) {
    if(reviews == null || reviews.isEmpty()) {
      return 0.0;
    }

    Map<Double, Integer> ratingCounts = new HashMap<>();

    for(ReviewPromotion review : reviews) {
      double rating = review.getStars();
      ratingCounts.put(rating, ratingCounts.getOrDefault(rating, 0) + 1);
    }

    double weightedSum = 0.0;
    int totalWeight = 0;

    for(Map.Entry<Double, Integer> rating : ratingCounts.entrySet()) {
      weightedSum += rating.getKey() * rating.getValue();
      totalWeight += rating.getValue();
    }

    double average = totalWeight == 0 ? 0.0 : weightedSum / totalWeight;

    return Math.round(average * 10.0) / 10.0;
  }
}
