package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.ReviewPromotion;
import com.clubeevantagens.authmicroservice.model.dto.CreateReviewInput;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.repository.ReviewPromotionRepository;
import com.clubeevantagens.authmicroservice.service.CreateReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CreateReviewTest {

  @Mock
  private ReviewPromotionRepository reviewPromotionRepository;

  @Mock
  private PromotionRepository promotionRepository;

  @InjectMocks
  private CreateReview sut;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateAReviewValid() {
    CreateReviewInput input = new CreateReviewInput(1L, 1L, 4.0, "Good");
    ReviewPromotion reviewPromotion = new ReviewPromotion(input.clientId(), input.promotionId(), input.stars(), input.comment());
    Promotion promotion = new Promotion(1L, "Pet shop 2 em 1", 150, "promotion-image-url");

    when(reviewPromotionRepository.findAllByPromotionId(input.promotionId())).thenReturn(Collections.singletonList(reviewPromotion));
    when(promotionRepository.findByPromotionId(input.promotionId())).thenReturn(promotion);

    sut.execute(input);

    verify(reviewPromotionRepository, times(1)).save(any(ReviewPromotion.class));
    verify(promotionRepository, times(1)).findByPromotionId(input.promotionId());
    verify(promotionRepository, times(1)).save(promotion);

    ArgumentCaptor<Promotion> promotionCaptor = ArgumentCaptor.forClass(Promotion.class);
    verify(promotionRepository, times(1)).save(promotionCaptor.capture());
    Promotion capturedPromotion = promotionCaptor.getValue();
    assertThat(capturedPromotion.getTotalReviews()).isEqualTo(1);
    assertThat(capturedPromotion.getReviewsRating()).isEqualTo(4.0);
  }

  @Test
  void shouldUpdateThePromotionRating() {
    Promotion promotion = new Promotion(1L, 1L, "Pet shop 2 em 1", 150, 4.5, 2, 0, 0, LocalDateTime.now(), "promotion-image-url", LocalDateTime.now());
    CreateReviewInput input = new CreateReviewInput(1L, 1L, 3.0, "Not bad");

    ReviewPromotion review1 = new ReviewPromotion(3L, 1L, 4.0, "Good");
    ReviewPromotion review2 = new ReviewPromotion(2L, 1L, 5.0, "Very good");
    ReviewPromotion newReview = new ReviewPromotion(input.clientId(), input.promotionId(), input.stars(), input.comment());

    List<ReviewPromotion> reviews = Arrays.asList(review1, review2, newReview);

    when(reviewPromotionRepository.findAllByPromotionId(input.promotionId())).thenReturn(reviews);
    when(promotionRepository.findByPromotionId(input.promotionId())).thenReturn(promotion);

    sut.execute(input);

    verify(reviewPromotionRepository, times(1)).save(any(ReviewPromotion.class));
    verify(promotionRepository, times(1)).findByPromotionId(input.promotionId());
    verify(promotionRepository, times(1)).save(promotion);

    ArgumentCaptor<Promotion> promotionCaptor = ArgumentCaptor.forClass(Promotion.class);
    verify(promotionRepository, times(1)).save(promotionCaptor.capture());
    Promotion capturedPromotion = promotionCaptor.getValue();
    assertThat(capturedPromotion.getTotalReviews()).isEqualTo(3);
    assertThat(capturedPromotion.getReviewsRating()).isEqualTo(4);
  }

  @Test
  void shouldThrowAnErrorIfPromotionNotFound() {
    CreateReviewInput input = new CreateReviewInput(1L, 1L, 5.0, "Very good");

    when(promotionRepository.findByPromotionId(input.promotionId())).thenThrow(new RuntimeException("Promotion not found"));

    Exception error = assertThrows(RuntimeException.class, () -> sut.execute(input));
    assertThat(error.getMessage()).isEqualTo("Promotion not found");
  }

  @Test
  void shouldNotUpdateThePromotionIfAnErrorOccursDuringTheProcess() {
    Promotion promotion = new Promotion(1L, "Pet shop 2 em 1", 150, "promotion-image-url");
    CreateReviewInput input = new CreateReviewInput(1L, 1L, 5.0, "Very good");

    when(reviewPromotionRepository.findAllByPromotionId(input.promotionId())).thenReturn(Collections.emptyList());
    when(promotionRepository.findByPromotionId(input.promotionId())).thenReturn(promotion);

    doThrow(new RuntimeException("Error saving the review")).when(reviewPromotionRepository).save(any(ReviewPromotion.class));

    Exception error = assertThrows(RuntimeException.class, () -> sut.execute(input));
    assertThat(error.getMessage()).isEqualTo("Error saving the review");
    verify(promotionRepository, never()).save(any(Promotion.class));
  }
}
