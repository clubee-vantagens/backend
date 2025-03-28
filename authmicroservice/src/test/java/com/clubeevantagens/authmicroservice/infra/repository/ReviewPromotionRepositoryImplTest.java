package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.database.ReviewPromotionJPARepository;
import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import com.clubeevantagens.authmicroservice.repository.ReviewPromotionRepository;
import com.clubeevantagens.authmicroservice.repository.implementation.ReviewPromotionRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewPromotionRepositoryImplTest {
  @Autowired
  private ReviewPromotionJPARepository reviewPromotionJPARepository;
  private ReviewPromotionRepository reviewPromotionRepository;

  @BeforeEach
  void setUp() {
    reviewPromotionRepository = new ReviewPromotionRepositoryImpl(reviewPromotionJPARepository);
  }

  @Test
  void testSave() {
    ReviewPromotion review = new ReviewPromotion(1L, 1L, 5.0, "Very good");
    reviewPromotionRepository.save(review);
    List<ReviewPromotion> found = reviewPromotionRepository.findAllByPromotionId(review.getPromotionId());

    assertThat(found.size()).isEqualTo(1);
    assertThat(found.getFirst().getReviewId()).isNotNull();
    assertThat(found.getFirst().getClientId()).isEqualTo(1L);
    assertThat(found.getFirst().getPromotionId()).isEqualTo(1L);
    assertThat(found.getFirst().getStars()).isEqualTo(5.0);
    assertThat(found.getFirst().getComment()).isEqualTo("Very good");
    assertThat(found.getFirst().getCreatedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  void testFindAllByPromotionId() {
    ReviewPromotion review1 = new ReviewPromotion(2L, 1L, 5.0, "Very good");
    ReviewPromotion review2 = new ReviewPromotion(3L, 1L, 3.0, "Not bad");
    ReviewPromotion review3 = new ReviewPromotion(4L, 1L, 4.5, "Good");

    reviewPromotionRepository.save(review1);
    reviewPromotionRepository.save(review2);
    reviewPromotionRepository.save(review3);

    List<ReviewPromotion> reviewsPromotions = this.reviewPromotionRepository.findAllByPromotionId(1L);
    assertThat(reviewsPromotions.size()).isEqualTo(3);
  }
}
