package com.clubeevantagens.authmicroservice.infra.database;

import com.clubeevantagens.authmicroservice.infra.database.model.FavoriteCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteCompanyJPARepository extends JpaRepository<FavoriteCompanyModel, Long> {
}
