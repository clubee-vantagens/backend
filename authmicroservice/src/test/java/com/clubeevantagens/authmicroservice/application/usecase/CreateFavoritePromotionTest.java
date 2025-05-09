package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.FavoritePromotion;
import com.clubeevantagens.authmicroservice.app.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoritePromotionRepository;
import com.clubeevantagens.authmicroservice.app.usecase.CreateFavoritePromotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateFavoritePromotionTest {

  @Mock
  private FavoritePromotionRepository favoritePromotionRepository;

  @InjectMocks
  private CreateFavoritePromotion sut;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateAFavoritePromotion() {
    CreateFavoritePromotionInput input = new CreateFavoritePromotionInput(1L, 1L);
    sut.execute(input);
    verify(favoritePromotionRepository, times(1)).save(any(FavoritePromotion.class));
  }
}
