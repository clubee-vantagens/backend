package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.FavoriteCompany;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FavoriteCompanyTest {
  @Test
  void shouldCreateAFavoriteCompany() {
    FavoriteCompany favoriteCompany = new FavoriteCompany(1L, 1L);
    assertThat(favoriteCompany.getFavoriteCompany()).isNull();
    assertThat(favoriteCompany.getClientId()).isEqualTo(1L);
    assertThat(favoriteCompany.getCompanyId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowAnErrorIfClientIdIsNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new FavoriteCompany(null, 1L));
    assertThat(error.getMessage()).isEqualTo("Client identifier cannot be null");
  }

  @Test
  void shouldThrowAnErrorIfComppanyIdIsNull() {
    RuntimeException error = assertThrows(RuntimeException.class, () -> new FavoriteCompany(1L, null));
    assertThat(error.getMessage()).isEqualTo("Company identifier cannot be null");
  }
}
