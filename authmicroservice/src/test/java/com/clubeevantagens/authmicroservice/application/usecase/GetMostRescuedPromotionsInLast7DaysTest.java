package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.model.dto.*;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.service.GetMostRescuedPromotionsInLast7Days;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetMostRescuedPromotionsInLast7DaysTest {
  @Mock
  private PromotionRepository promotionRepository;

  @InjectMocks
  private GetMostRescuedPromotionsInLast7Days sut;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnAListWithPromotionsRedemptionsLast7Days() {
    GetMostRescuedPromotionsInLast7DaysInput input = new GetMostRescuedPromotionsInLast7DaysInput(1L);

    GetMostRescuedPromotionsInLast7DaysProjection promotion1 = new GetMostRescuedPromotionsInLast7DaysProjection(1L, "Pet shop 2 em 1", 150, 5.0, 2, "promotion-image-url", false);
    GetMostRescuedPromotionsInLast7DaysProjection promotion2 = new GetMostRescuedPromotionsInLast7DaysProjection(2L, "Prato executivo", 150, 4.0, 9, "promotion-image-url", false);
    GetMostRescuedPromotionsInLast7DaysProjection promotion3 = new GetMostRescuedPromotionsInLast7DaysProjection(3L, "Lanches da Manu", 190, 4.3, 6, "promotion-image-url", false);

    List<GetMostRescuedPromotionsInLast7DaysProjection> promotionsLast7Days = Arrays.asList(promotion1, promotion2, promotion3);

    when(promotionRepository.findMostRescuedPromotionsInLast7Days(input.clientId())).thenReturn(promotionsLast7Days);

    List<GetMostRescuedPromotionsInLast7DaysOutput> output = sut.execute(input);

    verify(promotionRepository, times(1)).findMostRescuedPromotionsInLast7Days(input.clientId());
    assertThat(output.size()).isEqualTo(3);

    assertThat(output.get(0).promotionId()).isEqualTo(1L);
    assertThat(output.get(0).promotionName()).isEqualTo("Pet shop 2 em 1");
    assertThat(output.get(0).distance()).isEqualTo(1.5);
    assertThat(output.get(0).reviewsRating()).isEqualTo(5.0);
    assertThat(output.get(0).totalReviews()).isEqualTo(2);
    assertThat(output.get(0).points()).isEqualTo(150);
    assertThat(output.get(0).promotionImage()).isEqualTo("promotion-image-url");
    assertThat(output.get(0).companyProfile()).isEqualTo("company-profile-mock-url");

    assertThat(output.get(1).promotionId()).isEqualTo(2L);
    assertThat(output.get(1).promotionName()).isEqualTo("Prato executivo");
    assertThat(output.get(1).distance()).isEqualTo(1.5);
    assertThat(output.get(1).reviewsRating()).isEqualTo(4.0);
    assertThat(output.get(1).totalReviews()).isEqualTo(9);
    assertThat(output.get(1).points()).isEqualTo(150);
    assertThat(output.get(1).promotionImage()).isEqualTo("promotion-image-url");
    assertThat(output.get(1).companyProfile()).isEqualTo("company-profile-mock-url");

    assertThat(output.get(2).promotionId()).isEqualTo(3L);
    assertThat(output.get(2).promotionName()).isEqualTo("Lanches da Manu");
    assertThat(output.get(2).distance()).isEqualTo(1.5);
    assertThat(output.get(2).reviewsRating()).isEqualTo(4.3);
    assertThat(output.get(2).totalReviews()).isEqualTo(6);
    assertThat(output.get(2).points()).isEqualTo(190);
    assertThat(output.get(2).promotionImage()).isEqualTo("promotion-image-url");
    assertThat(output.get(2).companyProfile()).isEqualTo("company-profile-mock-url");
  }
}
