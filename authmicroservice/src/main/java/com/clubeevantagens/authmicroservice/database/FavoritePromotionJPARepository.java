package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.database.model.FavoritePromotionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritePromotionJPARepository extends JpaRepository<FavoritePromotionModel, Long> {
}
