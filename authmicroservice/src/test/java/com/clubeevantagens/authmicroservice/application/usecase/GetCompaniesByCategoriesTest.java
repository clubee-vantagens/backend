package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.Promotion;
import com.clubeevantagens.authmicroservice.domain.entity.ReviewPromotion;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesInput;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesOutput;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesProjection;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import com.clubeevantagens.authmicroservice.app.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.app.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.app.repository.ReviewPromotionRepository;
import com.clubeevantagens.authmicroservice.app.usecase.GetCompaniesByCategories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetCompaniesByCategoriesTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PromotionRepository promotionRepository;

  @Mock
  private ReviewPromotionRepository reviewPromotionRepository;

  @InjectMocks
  private GetCompaniesByCategories sut;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnCompaniesOrderedByRedemptionsAndExcludeZeroRedemptions() {
    GetCompaniesByCategoriesInput input = new GetCompaniesByCategoriesInput(1L);
    List<String> categories = List.of("restaurante", "petshop");

    List<GetCompaniesByCategoriesProjection> projections = List.of(
            new GetCompaniesByCategoriesProjection(1L, "Rest A", "restaurante", true),
            new GetCompaniesByCategoriesProjection(2L, "Rest B", "restaurante", true),
            new GetCompaniesByCategoriesProjection(3L, "Rest C", "restaurante", false),
            new GetCompaniesByCategoriesProjection(4L, "Pet A", "petshop", false),
            new GetCompaniesByCategoriesProjection(5L, "Pet B", "petshop", false),
            new GetCompaniesByCategoriesProjection(6L, "Pet C", "petshop", true)
    );

    when(categoryRepository.findCategoriesByClientId(input.clientId())).thenReturn(categories);
    when(companyRepository.findCompanyByCategory(categories, input.clientId())).thenReturn(projections);

    when(promotionRepository.findAllByCompanyId(projections.get(0).companyId())) // Rest A
            .thenReturn(List.of(promotionWithRedemptions(101L, 1L, 10)));
    when(promotionRepository.findAllByCompanyId(projections.get(1).companyId())) // Rest B
            .thenReturn(List.of(promotionWithRedemptions(102L, 2L, 8)));
    when(promotionRepository.findAllByCompanyId(projections.get(2).companyId())) // Rest C
            .thenReturn(List.of(promotionWithRedemptions(103L, 3L, 1)));
    when(promotionRepository.findAllByCompanyId(projections.get(3).companyId())) // Pet A
            .thenReturn(List.of(promotionWithRedemptions(104L, 4L, 12)));
    when(promotionRepository.findAllByCompanyId(projections.get(4).companyId())) // Pet B
            .thenReturn(List.of(promotionWithRedemptions(105L, 5L, 6)));
    when(promotionRepository.findAllByCompanyId(projections.get(5).companyId())) // Pet C
            .thenReturn(List.of(promotionWithRedemptions(106L, 6L, 0)));

    for (long promoId = 101; promoId <= 106; promoId++) {
      int reviewCount = switch ((int) promoId) {
        case 101 -> 10;
        case 102 -> 8;
        case 103 -> 1;
        case 104 -> 12;
        case 105 -> 6;
        case 106 -> 0;
        default -> 0;
      };

      List<ReviewPromotion> reviews = new ArrayList<>();
      for (int i = 0; i < reviewCount; i++) {
        reviews.add(new ReviewPromotion((long) (promoId * 100 + i), promoId, 5.0, "Bom"));
      }

      when(reviewPromotionRepository.findAllByPromotionId(promoId)).thenReturn(reviews);
    }

    List<GetCompaniesByCategoriesOutput> result = sut.execute(input);

    System.out.println(result.toString());

    assertEquals(6, result.size());

    result.sort((a, b) -> Integer.compare(b.totalReviews(), a.totalReviews()));

    List<String> orderedCompanyNames = result.stream().map(GetCompaniesByCategoriesOutput::companyName).toList();

    List<String> expectedOrder = List.of(
            "Pet A", "Rest A", "Rest B", "Pet B", "Rest C"
    );

    assertEquals(expectedOrder, orderedCompanyNames.subList(0, expectedOrder.size()));
  }

  @Test
  void shouldReturnEmptyList_whenClientHasNoPreferences() {
    Long clientId = 99L;
    when(categoryRepository.findCategoriesByClientId(clientId)).thenReturn(List.of());

    GetCompaniesByCategoriesInput input = new GetCompaniesByCategoriesInput(clientId);
    List<GetCompaniesByCategoriesOutput> result = sut.execute(input);

    assertThat(result).isEmpty();
  }

  private Promotion promotionWithRedemptions(Long promotionId, Long companyId, int redemptions) {
    return new Promotion(
            promotionId,
            companyId,
            "Promo " + promotionId,
            150,
            5.0,
            10,
            redemptions,
            5,
            LocalDateTime.now(),
            "promo-img.jpg",
            LocalDateTime.now()
    );
  }
}
