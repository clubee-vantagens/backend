package com.clubeevantagens.authmicroservice.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_companies", schema = "public")
public class FavoriteCompanyModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favorite_company_id", nullable = false, unique = true)
  private Long favoriteCompanyId;
  @Column(name = "client_id", nullable = false)
  private Long clientId;
  @Column(name = "company_id", nullable = false)
  private Long companyId;

  public FavoriteCompanyModel() {}

  public FavoriteCompanyModel(Long favoriteCompanyId, Long clientId, Long companyId) {
    this.favoriteCompanyId = favoriteCompanyId;
    this.clientId = clientId;
    this.companyId = companyId;
  }

  public Long getFavoriteCompanyId() {
    return this.favoriteCompanyId;
  }

  public void setFavoriteCompanyId(Long favoriteCompanyId) {
    this.favoriteCompanyId = favoriteCompanyId;
  }

  public Long getClientId() {
    return this.clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }
}
