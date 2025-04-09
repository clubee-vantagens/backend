package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.database.model.FavoriteCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteCompanyJPARepository extends JpaRepository<FavoriteCompanyModel, Long> {
}
