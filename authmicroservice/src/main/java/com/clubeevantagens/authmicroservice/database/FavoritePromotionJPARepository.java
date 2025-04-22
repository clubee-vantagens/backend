package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.database.model.FavoritePromotionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.Tuple;
import java.util.List;

public interface FavoritePromotionJPARepository extends JpaRepository<FavoritePromotionModel, Long> {
  @Query(value = """
  SELECT COUNT(*) AS totalFavorites
  FROM (
    SELECT 1 FROM favorite_promotions WHERE client_id = :clientId
    UNION ALL
    SELECT 1 FROM favorite_companies WHERE client_id = :clientId
  ) AS combined
  """, nativeQuery = true)
  List<Tuple> getTotalFavorites(@Param("clientId") Long clientId);
}
