package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.database.FavoritePromotionJPARepository;
import com.clubeevantagens.authmicroservice.database.PromotionJPARepository;
import com.clubeevantagens.authmicroservice.database.model.FavoritePromotionModel;
import com.clubeevantagens.authmicroservice.model.Promotion;
import com.clubeevantagens.authmicroservice.model.dto.GetMostRescuedPromotionsInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.repository.implementation.PromotionRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PromotionRepositoryImplTest {

  @Autowired
  private PromotionJPARepository promotionJPARepository;

  @Autowired
  private FavoritePromotionJPARepository favoritePromotionJPARepository;

  private PromotionRepositoryImpl promotionRepository;

  @BeforeEach
  void setUp() {
    promotionRepository = new PromotionRepositoryImpl(promotionJPARepository);
  }

  @AfterEach
  void tearDown() {
    promotionJPARepository.deleteAll();
    favoritePromotionJPARepository.deleteAll();
  }

  @Test
  @DisplayName("Should save a promotion successfully")
  void shouldSavePromotion() {
    Promotion promotion = new Promotion(1L, "Test Promotion", 100, "image.png");
    Long promotionId = promotionRepository.save(promotion);
    Promotion savedPromotion = promotionRepository.findByPromotionId(promotionId);

    assertThat(savedPromotion).isNotNull();
    assertThat(savedPromotion.getCompanyId()).isEqualTo(1L);
    assertThat(savedPromotion.getPromotionName()).isEqualTo("Test Promotion");
    assertThat(savedPromotion.getPoints()).isEqualTo(100);
    assertThat(savedPromotion.getReviewsRating()).isEqualTo(5.0);
    assertThat(savedPromotion.getTotalReviews()).isEqualTo(0);
    assertThat(savedPromotion.getTotalRedemptions()).isEqualTo(0);
    assertThat(savedPromotion.getRedemptionsLast7Days()).isEqualTo(0);
    assertThat(savedPromotion.getCycleStart()).isInstanceOf(LocalDateTime.class);
    assertThat(savedPromotion.getPromotionImage()).isEqualTo("image.png");
    assertThat(savedPromotion.getCreatedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  @DisplayName("Should retrieve a promotion by its ID")
  void shouldFindPromotionById() {
    Promotion promotion = new Promotion(1L, "Test Promotion", 100, "image.png");
    Long promotionId = promotionRepository.save(promotion);
    Promotion foundPromotion = promotionRepository.findByPromotionId(promotionId);
    assertThat(foundPromotion.getPromotionName()).isEqualTo("Test Promotion");
  }

  @Test
  @DisplayName("Should throw an error if the promotion is not found")
  void shouldThrowErrorIfPromotionNotFound() {
    Exception exception = assertThrows(RuntimeException.class, () -> {
      promotionRepository.findByPromotionId(99L);
    });
    assertThat(exception.getMessage()).isEqualTo("Promotion not found");
  }

  @Test
  @DisplayName("Should return 10 promotions created in the last 7 days in descending order")
  void shouldReturn10LatestPromotions() {
    for (int i = 1; i <= 15; i++) {
      promotionRepository.save(new Promotion((long) i, "Promotion " + i, 100, "image" + i + ".png"));
    }
    List<GetPromotionsCreatedInLast7DaysProjection> promotions = promotionRepository.findPromotionsCreatedInLast7Days(10L);
    assertThat(promotions).hasSize(10);
  }

  @Test
  @DisplayName("Should return empty if there are no promotions or favorites")
  void shouldReturnEmptyIfNoPromotionsOrFavorites() {
    List<GetPromotionsCreatedInLast7DaysProjection> promotions = promotionRepository.findPromotionsCreatedInLast7Days(10L);
    assertThat(promotions).isEmpty();
  }

  @Test
  @DisplayName("Should return 7 promotions created in the last 7 days plus 3 favorite promotions")
  void shouldReturn7LatestPromotionsPlus3Favorites() {
    for (int i = 8; i <= 10; i++) {
      Promotion favoritePromotion = new Promotion(10L, "Favorite " + i, 100, "image" + i);
      Long promotionId = promotionRepository.save(favoritePromotion);
      favoritePromotionJPARepository.save(new FavoritePromotionModel((long) i, 10L, promotionId));
    }

    for (int i = 1; i <= 7; i++) {
      promotionRepository.save(new Promotion(10L, "Promotion " + i, 100, "image" + i));
    }

    List<GetPromotionsCreatedInLast7DaysProjection> promotions = promotionRepository.findPromotionsCreatedInLast7Days(10L);
    assertThat(promotions).hasSize(10);
    assertThat(promotions.get(7).promotionName()).isEqualTo("Favorite 10");
    assertThat(promotions.get(8).promotionName()).isEqualTo("Favorite 9");
    assertThat(promotions.get(9).promotionName()).isEqualTo("Favorite 8");
  }

  @Test
  @DisplayName("Should return only favorite promotions if there are no recent promotions")
  void shouldReturnOnlyFavoritesIfNoRecentPromotions() {
    for (int i = 0; i <= 10; i++) {
      Promotion promotion = new Promotion((long) i, 10L, "Promotion " + i, 100, 4.5, 30, 8, 2, LocalDateTime.now().minusDays(10), "image" + i, LocalDateTime.now().minusDays(20));
      promotionRepository.save(promotion);
    }

    for (int i = 1; i <= 3; i++) {
      Promotion favoritePromotion = new Promotion((long) i, 10L, "Favorite " + i, 100, 4.5, 30, 8, 2, LocalDateTime.now().minusDays(10), "image" + i, LocalDateTime.now().minusDays(10));
      promotionRepository.save(favoritePromotion);
      favoritePromotionJPARepository.save(new FavoritePromotionModel((long) i, 10L, favoritePromotion.getPromotionId()));
    }
    List<GetPromotionsCreatedInLast7DaysProjection> promotions = promotionRepository.findPromotionsCreatedInLast7Days(10L);
    assertThat(promotions).hasSize(10);
  }

  @Test
  @DisplayName("Should return the top 10 most redeemed promotions")
  void shouldReturnTop10MostRescuedPromotions() {
    for (int i = 1; i <= 15; i++) {
      Promotion promotion = new Promotion((long) i, 10L, "Promotion " + i, 100, 4.5, 30, i, i, LocalDateTime.now(), "image" + i + ".png", LocalDateTime.now());
      promotionRepository.save(promotion);
      if (i <= 5) {
        favoritePromotionJPARepository.save(new FavoritePromotionModel((long) i, 1L, promotion.getPromotionId()));
      }
    }

    List<GetMostRescuedPromotionsInLast7DaysProjection> result = promotionRepository.findMostRescuedPromotionsInLast7Days(1L);

    assertThat(result).hasSize(10);
    assertThat(result.get(0).promotionName()).isEqualTo("Promotion 15");
    assertThat(result.get(1).promotionName()).isEqualTo("Promotion 14");
    assertThat(result.get(2).promotionName()).isEqualTo("Promotion 13");
    assertThat(result.get(3).promotionName()).isEqualTo("Promotion 12");
    assertThat(result.get(4).promotionName()).isEqualTo("Promotion 11");
    assertThat(result.get(5).promotionName()).isEqualTo("Promotion 10");
    assertThat(result.get(6).promotionName()).isEqualTo("Promotion 9");
    assertThat(result.get(7).promotionName()).isEqualTo("Promotion 8");
    assertThat(result.get(8).promotionName()).isEqualTo("Promotion 7");
    assertThat(result.get(9).promotionName()).isEqualTo("Promotion 6");
  }

  @Test
  @DisplayName("Should return the top 7 most redeemed promotions and top 3 favorite promotions")
  void shouldReturnTop7MostRescuedPromotionsAndTop3FavoritePromotions() {
    for (int i = 1; i <= 7; i++) {
      Promotion promotion = new Promotion(
              (long) i, 10L, "Promotion " + i, 100, 4.5, 30, i, i, LocalDateTime.now(), "image" + i + ".png", LocalDateTime.now()
      );
      promotionRepository.save(promotion);
    }



    for (int i = 8; i <= 10; i++) {
      Promotion favoritePromotion = new Promotion(
              (long) i, 10L, "Favorite " + i, 100, 4.5, 30, 10, 0, LocalDateTime.now(), "image" + i + ".png", LocalDateTime.now()
      );
      Long promotionId = promotionRepository.save(favoritePromotion);
      favoritePromotionJPARepository.save(new FavoritePromotionModel((long) i, 2L, promotionId));
    }

    List<GetMostRescuedPromotionsInLast7DaysProjection> result = promotionRepository.findMostRescuedPromotionsInLast7Days(2L);

    assertThat(result).hasSize(10);

    for (int i = 0; i < 7; i++) {
      assertThat(result.get(i).promotionName()).startsWith("Promotion");
    }

    for (int i = 7; i < 10; i++) {
      assertThat(result.get(i).promotionName()).startsWith("Favorite");
      assertThat(result.get(i).isFavorite()).isTrue();
    }
  }

  @Test
  @DisplayName("Should return only favorite promotions when there are no redemptions in the last 7 days")
  void shouldReturnOnlyFavoritesWhenNoRecentRedemptions() {
    for (int i = 1; i <= 5; i++) {
      Promotion oldPromotion = new Promotion(
              (long) i, 10L, "Old Promotion " + i, 100, 4.5, 30, 0, 0,
              LocalDateTime.now().minusDays(10), "image" + i + ".png", LocalDateTime.now().minusDays(20)
      );
      promotionRepository.save(oldPromotion);
    }

    for (int i = 6; i <= 8; i++) {
      Promotion favoritePromotion = new Promotion(
              (long) i, 10L, "Favorite " + i, 100, 4.5, 30, 0, 0,
              LocalDateTime.now(), "image" + i + ".png", LocalDateTime.now()
      );
      Long promotionId = promotionRepository.save(favoritePromotion);
      favoritePromotionJPARepository.save(new FavoritePromotionModel((long) i, 1L, promotionId));
    }

    List<GetMostRescuedPromotionsInLast7DaysProjection> result = promotionRepository.findMostRescuedPromotionsInLast7Days(1L);

    assertThat(result).hasSize(8);
    assertThat(result.get(0).isFavorite()).isTrue();
    assertThat(result.get(1).isFavorite()).isTrue();
    assertThat(result.get(2).isFavorite()).isTrue();
  }

  @Test
  @DisplayName("Should return the top 10 most redeemed promotions when there are more than 10")
  void shouldReturnTop10MostRescuedPromotionsWhenMoreThan10Exist() {
    for (int i = 1; i <= 15; i++) {
      Promotion promotion = new Promotion(
              (long) i, 10L, "Promotion " + i, 100, 4.5, 30, i * 10, i * 10,
              LocalDateTime.now(), "image" + i + ".png", LocalDateTime.now()
      );
      promotionRepository.save(promotion);
    }

    List<GetMostRescuedPromotionsInLast7DaysProjection> result = promotionRepository.findMostRescuedPromotionsInLast7Days(1L);

    assertThat(result).hasSize(10);
    assertThat(result.get(0).promotionName()).isEqualTo("Promotion 15");
    assertThat(result.get(1).promotionName()).isEqualTo("Promotion 14");
    assertThat(result.get(9).promotionName()).isEqualTo("Promotion 6");
  }
}
