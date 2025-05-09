package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.app.dto.GetTotalFavoritesInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoritePromotionRepository;
import com.clubeevantagens.authmicroservice.app.usecase.GetTotalFavorites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetTotalFavoritesTest {

  @Mock
  private FavoritePromotionRepository favoritePromotionRepository;

  @InjectMocks
  private GetTotalFavorites sut;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("should return a total of five favorites for the client")
  void shouldReturnATotalOfFiveFavoritesForTheClient() {
    GetTotalFavoritesInput input = new GetTotalFavoritesInput(1L);

    when(favoritePromotionRepository.getTotalFavorites(1L)).thenReturn(5);

    int result = sut.execute(input);

    verify(favoritePromotionRepository).getTotalFavorites(1L);
    assertThat(result).isEqualTo(5);
  }

  @Test
  @DisplayName("should return a total of five favorites for the client")
  void shouldReturnATotalOfZeroFavoritesForTheClient() {
    GetTotalFavoritesInput input = new GetTotalFavoritesInput(99L);

    when(favoritePromotionRepository.getTotalFavorites(99L)).thenReturn(0);

    int result = sut.execute(input);

    verify(favoritePromotionRepository).getTotalFavorites(99L);
    assertThat(result).isEqualTo(0);
  }
}
