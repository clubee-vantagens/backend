package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.domain.entity.Promotion;
import com.clubeevantagens.authmicroservice.domain.service.RatingCalculator;
import com.clubeevantagens.authmicroservice.domain.entity.ReviewPromotion;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesInput;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesOutput;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesProjection;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import com.clubeevantagens.authmicroservice.app.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.app.repository.PromotionRepository;
import com.clubeevantagens.authmicroservice.app.repository.ReviewPromotionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCompaniesByCategories {
  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;
  private final ReviewPromotionRepository reviewPromotionRepository;
  private final PromotionRepository promotionRepository;

  public GetCompaniesByCategories(CategoryRepository categoryRepository, CompanyRepository companyRepository, ReviewPromotionRepository reviewPromotionRepository, PromotionRepository promotionRepository) {
    this.categoryRepository = categoryRepository;
    this.companyRepository = companyRepository;
    this.reviewPromotionRepository = reviewPromotionRepository;
    this.promotionRepository = promotionRepository;
  }

  public List<GetCompaniesByCategoriesOutput> execute(GetCompaniesByCategoriesInput input) {
    List<String> preferences = this.categoryRepository.findCategoriesByClientId(input.clientId());
    List<GetCompaniesByCategoriesProjection> companiesProjection = this.companyRepository.findCompanyByCategory(preferences, input.clientId());
    List<GetCompaniesByCategoriesOutput> companies = new ArrayList<>();

    for (GetCompaniesByCategoriesProjection projection : companiesProjection) {
      List<Promotion> allPromotions = this.promotionRepository.findAllByCompanyId(projection.companyId());

      List<ReviewPromotion> allReviews = new ArrayList<>();
      for (Promotion promotion : allPromotions) {
        List<ReviewPromotion> reviews = this.reviewPromotionRepository.findAllByPromotionId(promotion.getPromotionId());
        allReviews.addAll(reviews);
      }

      double rating = RatingCalculator.calculate(allReviews);

      companies.add(new GetCompaniesByCategoriesOutput(
              projection.companyId(),
              projection.companyName(),
              projection.category(),
              1.5,
              rating,
              allReviews.size(),
              "implementar-depois-usando-s3",
              "implementar-depois-usando-s3",
              projection.isFavorite()
      ));
    }
    return companies;
  }
}