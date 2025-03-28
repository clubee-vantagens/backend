package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.database.FavoritePromotionJPARepository;
import com.clubeevantagens.authmicroservice.model.FavoritePromotion;
import com.clubeevantagens.authmicroservice.repository.implementation.FavoritePromotionRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoritePromotionRepositoryImplTest {
  @Autowired
  private FavoritePromotionJPARepository favoritePromotionJPARepository;

  private FavoritePromotionRepositoryImpl favoritePromotionRepository;

  @BeforeEach
  void setUp() {
    favoritePromotionRepository = new FavoritePromotionRepositoryImpl(favoritePromotionJPARepository);
  }

  @AfterEach
  void tearDown() {
    favoritePromotionJPARepository.deleteAll();
  }

  @Test
  void shouldSaveFavoritePromotion() {
    FavoritePromotion favoritePromotion = new FavoritePromotion(1L, 1L);
    Long favoritePromotionId = this.favoritePromotionRepository.save(favoritePromotion);
    assertThat(favoritePromotionId).isNotNull();
  }
}
