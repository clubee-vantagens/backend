package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.database.model.ReviewPromotionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewPromotionJPARepository extends JpaRepository<ReviewPromotionModel, Long> {
  @Query(value = """
          SELECT 
            r.review_id AS reviewId,
            r.client_id AS clientId,
            r.promotion_id AS promotionId,
            r.stars,
            r.comment,
            r.created_at AS createdAt
          FROM 
            reviews r
          WHERE 
            r.promotion_id = :promotion_id
          """, nativeQuery = true)
  List<ReviewPromotionModel> findAllByPromotionId(@Param("promotion_id")Long promotionId);
}
