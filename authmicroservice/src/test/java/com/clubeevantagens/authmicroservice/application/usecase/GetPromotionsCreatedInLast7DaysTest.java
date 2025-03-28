package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysInput;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysOutput;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysProjection;
import com.clubeevantagens.authmicroservice.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.service.GetPromotionsCreatedInLast7Days;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetPromotionsCreatedInLast7DaysTest {

  @Mock
  private PromotionRepository promotionRepository;

  @InjectMocks
  private GetPromotionsCreatedInLast7Days sut;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should return a list with the promotions created in last 7 days")
  void shouldReturnAListWithTheLatestPromotions() {
    GetPromotionsCreatedInLast7DaysInput input = new GetPromotionsCreatedInLast7DaysInput(1L);
    GetPromotionsCreatedInLast7DaysProjection promotion1 = new GetPromotionsCreatedInLast7DaysProjection(1L, "Pet shop 2 em 1", 150, 5.0, 2, "promotion-image-url");
    GetPromotionsCreatedInLast7DaysProjection promotion2 = new GetPromotionsCreatedInLast7DaysProjection(2L, "Prato executivo", 150, 4.0, 9, "promotion-image-url");
    GetPromotionsCreatedInLast7DaysProjection promotion3 = new GetPromotionsCreatedInLast7DaysProjection(3L, "Lanches da Manu", 190, 4.3, 6, "promotion-image-url");

    List<GetPromotionsCreatedInLast7DaysProjection> promotionsCreatedInLast7Days = Arrays.asList(promotion1, promotion2, promotion3);

    when(promotionRepository.findPromotionsCreatedInLast7Days(input.clientId())).thenReturn(promotionsCreatedInLast7Days);

    List<GetPromotionsCreatedInLast7DaysOutput> output = sut.execute(input);

    verify(promotionRepository, times(1)).findPromotionsCreatedInLast7Days(input.clientId());
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
