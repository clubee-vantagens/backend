package com.clubeevantagens.authmicroservice.model;

public class FavoriteCompany {
  private Long favoriteCompany;
  private final Long clientId;
  private final Long companyId;

  public FavoriteCompany(Long clientId, Long companyId) {
    if(clientId == null) throw new RuntimeException("Client identifier cannot be null");
    if(companyId == null) throw new RuntimeException("Company identifier cannot be null");
    this.clientId = clientId;
    this.companyId = companyId;
  }

  public Long getFavoriteCompany() {
    return this.favoriteCompany;
  }

  public Long getClientId() {
    return this.clientId;
  }

  public Long getCompanyId() {
    return this.companyId;
  }
}
