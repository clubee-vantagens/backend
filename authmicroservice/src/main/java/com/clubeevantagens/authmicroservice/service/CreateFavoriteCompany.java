package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.FavoriteCompany;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoriteCompanyInput;
import com.clubeevantagens.authmicroservice.repository.FavoriteCompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateFavoriteCompany {
  private final FavoriteCompanyRepository favoriteCompanyRepository;

  public CreateFavoriteCompany(FavoriteCompanyRepository favoriteCompanyRepository) {
    this.favoriteCompanyRepository = favoriteCompanyRepository;
  }

  public void execute(CreateFavoriteCompanyInput input) {
    FavoriteCompany favoriteCompany = new FavoriteCompany(input.clientId(), input.companyId());
    this.favoriteCompanyRepository.save(favoriteCompany);
  }
}
