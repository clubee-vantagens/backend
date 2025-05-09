package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.infra.database.FavoriteCompanyJPARepository;
import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteCompanyRepositoryImplTest {
  @Autowired
  private FavoriteCompanyJPARepository favoriteCompanyJPARepository;

  private FavoriteCompanyRepositoryImpl favoriteCompanyRepository;

  @BeforeEach
  void setup() {
    favoriteCompanyRepository = new FavoriteCompanyRepositoryImpl(favoriteCompanyJPARepository);
  }

  @AfterEach
  void teardown() {
    favoriteCompanyJPARepository.deleteAll();
  }

  @Test
  void shouldSaveFavoriteCompany() {
    FavoriteCompany favoriteCompany = new FavoriteCompany(1L, 1L);
    Long favoriteCompanyId = this.favoriteCompanyRepository.save(favoriteCompany);
    assertThat(favoriteCompanyId).isNotNull();
  }
}
