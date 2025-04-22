package com.clubeevantagens.authmicroservice.repository.implementation;

import com.clubeevantagens.authmicroservice.database.FavoritePromotionJPARepository;
import com.clubeevantagens.authmicroservice.database.model.FavoritePromotionModel;
import com.clubeevantagens.authmicroservice.model.FavoritePromotion;
import com.clubeevantagens.authmicroservice.repository.FavoritePromotionRepository;
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
