package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.FavoriteCompany;

public interface FavoriteCompanyRepository {
  Long save(FavoriteCompany favoriteCompany);
}
