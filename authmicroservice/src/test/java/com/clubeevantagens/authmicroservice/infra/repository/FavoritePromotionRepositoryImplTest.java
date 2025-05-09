package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.infra.database.FavoriteCompanyJPARepository;
import com.clubeevantagens.authmicroservice.infra.database.FavoritePromotionJPARepository;
import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;
import com.clubeevantagens.authmicroservice.domain.entity.FavoritePromotion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoritePromotionRepositoryImplTest {
  @Autowired
  private FavoritePromotionJPARepository favoritePromotionJPARepository;

  @Autowired
  private FavoriteCompanyJPARepository favoriteCompanyJPARepository;

  private FavoritePromotionRepositoryImpl favoritePromotionRepository;

  private FavoriteCompanyRepositoryImpl favoriteCompanyRepository;

  @BeforeEach
  void setUp() {
    favoritePromotionRepository = new FavoritePromotionRepositoryImpl(favoritePromotionJPARepository);
    favoriteCompanyRepository = new FavoriteCompanyRepositoryImpl(favoriteCompanyJPARepository);
  }

  @AfterEach
  void tearDown() {
    favoritePromotionJPARepository.deleteAll();
    favoriteCompanyJPARepository.deleteAll();
  }

  @Test
  void shouldSaveFavoritePromotion() {
    FavoritePromotion favoritePromotion = new FavoritePromotion(1L, 1L);
    Long favoritePromotionId = this.favoritePromotionRepository.save(favoritePromotion);
    assertThat(favoritePromotionId).isNotNull();
  }

  @Test
  @DisplayName("should only return the total of the customer's favorite promotions")
  void shouldReturnTotalOnlyPromotionFavorites() {
    this.favoritePromotionRepository.save(new FavoritePromotion(1L, 101L));
    this.favoritePromotionRepository.save(new FavoritePromotion(1L, 102L));

    int total = this.favoritePromotionRepository.getTotalFavorites(1L);
    assertThat(total).isEqualTo(2);
  }

  @Test
  @DisplayName("should only return the total of the customer's favorite promotions")
  void shouldReturnTotalOnlyCompanyFavorites() {
    this.favoriteCompanyRepository.save(new FavoriteCompany(1L, 101L));
    this.favoriteCompanyRepository.save(new FavoriteCompany(1L, 102L));

    int total = this.favoritePromotionRepository.getTotalFavorites(1L);
    assertThat(total).isEqualTo(2);
  }

  @Test
  @DisplayName("should both return the total of the customer's favorites")
  void shouldReturnTotalBothFavorites() {
    this.favoritePromotionRepository.save(new FavoritePromotion(1L, 101L));
    this.favoritePromotionRepository.save(new FavoritePromotion(1L, 102L));
    this.favoriteCompanyRepository.save(new FavoriteCompany(1L, 103L));
    this.favoriteCompanyRepository.save(new FavoriteCompany(1L, 104L));

    int total = this.favoritePromotionRepository.getTotalFavorites(1L);
    assertThat(total).isEqualTo(4);
  }

  @Test
  @DisplayName("should return zero if if you dont have favorites")
  void shoudlReturnZeroIfNoFavorites() {
    int total = this.favoritePromotionRepository.getTotalFavorites(1L);
    assertThat(total).isEqualTo(0);
  }
}
