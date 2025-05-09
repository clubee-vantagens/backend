package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;

public interface FavoriteCompanyRepository {
  Long save(FavoriteCompany favoriteCompany);
}
