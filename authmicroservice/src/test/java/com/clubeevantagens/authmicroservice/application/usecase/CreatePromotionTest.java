package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.dto.CreatePromotionInput;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.service.CreatePromotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CreatePromotionTest {
  @Mock
  private PromotionRepository promotionRepository;

  @InjectMocks
  private CreatePromotion sut;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldSavePromotion() {
    CreatePromotionInput input = new CreatePromotionInput(1L, "Pet Shop 2 em 1", 150, "pet-shop.png");
    sut.execute(input);
    verify(promotionRepository, times(1)).save(any(Promotion.class));
  }
}
