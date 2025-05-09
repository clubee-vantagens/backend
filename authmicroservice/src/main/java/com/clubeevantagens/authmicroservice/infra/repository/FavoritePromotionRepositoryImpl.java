package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.infra.database.FavoritePromotionJPARepository;
import com.clubeevantagens.authmicroservice.infra.database.model.FavoritePromotionModel;
import com.clubeevantagens.authmicroservice.domain.entity.FavoritePromotion;
import com.clubeevantagens.authmicroservice.app.repository.FavoritePromotionRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Tuple;
import java.util.List;

@Repository
public class FavoritePromotionRepositoryImpl implements FavoritePromotionRepository {
  private final FavoritePromotionJPARepository connection;

  public FavoritePromotionRepositoryImpl(FavoritePromotionJPARepository connection) {
    this.connection = connection;
  }

  @Override
  public Long save(FavoritePromotion favoritePromotion) {
    FavoritePromotionModel model = new FavoritePromotionModel(favoritePromotion.getFavoritePromotionId(), favoritePromotion.getClientId(), favoritePromotion.getPromotionId());
    return this.connection.save(model).getFavoritePromotionId();
  }

  @Override
  public int getTotalFavorites(Long clientId) {
    List<Tuple> result = this.connection.getTotalFavorites(clientId);

    if (result.isEmpty()) {
      return 0;
    }

    Long total = result.getFirst().get("totalFavorites", Long.class);
    return total.intValue();
  }
}
