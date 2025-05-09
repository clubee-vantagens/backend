package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;
import com.clubeevantagens.authmicroservice.app.dto.CreateFavoriteCompanyInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoriteCompanyRepository;
import com.clubeevantagens.authmicroservice.app.usecase.CreateFavoriteCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateFavoriteCompanyTest {

  @Mock
  private FavoriteCompanyRepository favoriteCompanyRepository;

  @InjectMocks
  private CreateFavoriteCompany sut;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateAFavoriteCompany() {
    CreateFavoriteCompanyInput input = new CreateFavoriteCompanyInput(1L, 1L);
    sut.execute(input);
    verify(favoriteCompanyRepository, times(1)).save(any(FavoriteCompany.class));
  }
}
