package com.clubeevantagens.authmicroservice.repository.implementation;

import com.clubeevantagens.authmicroservice.database.FavoriteCompanyJPARepository;
import com.clubeevantagens.authmicroservice.database.model.FavoriteCompanyModel;
import com.clubeevantagens.authmicroservice.model.FavoriteCompany;
import com.clubeevantagens.authmicroservice.repository.FavoriteCompanyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteCompanyRepositoryImpl implements FavoriteCompanyRepository {
  private final FavoriteCompanyJPARepository connection;

  public FavoriteCompanyRepositoryImpl(FavoriteCompanyJPARepository connection) {
    this.connection = connection;
  }


  @Override
  public Long save(FavoriteCompany favoriteCompany) {
    FavoriteCompanyModel model = new FavoriteCompanyModel(favoriteCompany.getFavoriteCompany(), favoriteCompany.getClientId(), favoriteCompany.getCompanyId());
    return this.connection.save(model).getFavoriteCompanyId();
  }
}
