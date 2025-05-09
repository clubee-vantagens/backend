package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;
import com.clubeevantagens.authmicroservice.app.dto.CreateFavoriteCompanyInput;
import com.clubeevantagens.authmicroservice.app.repository.FavoriteCompanyRepository;
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
