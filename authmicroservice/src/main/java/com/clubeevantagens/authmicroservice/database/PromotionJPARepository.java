package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.database.model.PromotionModel;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface PromotionJPARepository extends JpaRepository<PromotionModel, Long> {
  List<Tuple> findPromotionsCreatedInLast7Days(@Param("clientId") Long clientId);
  List<Tuple> findMostRescuedPromotionsInLast7Days(@Param("clientId") Long clientId);
}
