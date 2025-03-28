package com.clubeevantagens.authmicroservice.database;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Tuple;
import java.util.List;

@Profile("prod")
@Repository
public interface PromotionRepositoryPostgres extends PromotionJPARepository {
  @Query(value = """
        WITH ranked_promotions AS (
            SELECT 
                p.promotion_id AS promotionId,
                p.promotion_name AS promotionName,
                p.points,
                p.reviews_rating AS reviewsRating,
                p.total_reviews AS totalReviews,
                p.promotion_image AS promotionImage,
                p.created_at,
                CASE 
                    WHEN p.created_at >= CURRENT_DATE - INTERVAL '7 days' THEN 1
                    WHEN fp.client_id IS NOT NULL THEN 2
                    ELSE 3
                END AS ranking,
                ROW_NUMBER() OVER (PARTITION BY 
                    CASE 
                        WHEN p.created_at >= CURRENT_DATE - INTERVAL '7 days' THEN 1 
                        ELSE 2 
                    END ORDER BY p.created_at DESC) AS row_num
            FROM promotions p
            LEFT JOIN favorite_promotions fp ON fp.promotion_id = p.promotion_id AND fp.client_id = :clientId
        )
        SELECT promotionId, promotionName, points, reviewsRating, totalReviews, promotionImage, created_at
        FROM ranked_promotions
        WHERE row_num <= 10
        ORDER BY ranking, created_at DESC
    """, nativeQuery = true)
  List<Tuple> findPromotionsCreatedInLast7Days(@Param("clientId") Long clientId);

  @Query(value = """
        WITH ranked_promotions AS (
            SELECT 
                p.promotion_id AS promotionId,
                p.promotion_name AS promotionName,
                p.points,
                p.reviews_rating AS reviewsRating,
                p.total_reviews AS totalReviews,
                p.promotion_image AS promotionImage,
                p.redemptions_last_7_days,
                CASE 
                    WHEN p.redemptions_last_7_days > 0 THEN 1
                    WHEN fp.client_id IS NOT NULL THEN 2
                    ELSE 3
                END AS ranking,
                CASE 
                    WHEN fp.client_id IS NOT NULL THEN TRUE 
                    ELSE FALSE 
                END AS isFavorite,
                ROW_NUMBER() OVER (
                    PARTITION BY 
                        CASE 
                            WHEN p.redemptions_last_7_days > 0 THEN 1
                            WHEN fp.client_id IS NOT NULL THEN 2
                            ELSE 3
                        END 
                    ORDER BY 
                        p.redemptions_last_7_days DESC,
                        p.promotion_id
                ) AS row_num
            FROM promotions p
            LEFT JOIN favorite_promotions fp 
                ON fp.promotion_id = p.promotion_id 
                AND fp.client_id = :clientId
        )
        SELECT 
            promotionId, promotionName, points, reviewsRating, 
            totalReviews, promotionImage, isFavorite, redemptions_last_7_days
        FROM ranked_promotions
        WHERE row_num <= 10
        ORDER BY ranking, redemptions_last_7_days DESC, promotionId
    """, nativeQuery = true)
  List<Tuple> findMostRescuedPromotionsInLast7Days(@Param("clientId") Long clientId);
}
