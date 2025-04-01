package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionRequest;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.CreateFavoritePromotion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoritePromotionController {
  private final CreateFavoritePromotion createFavoritePromotion;

    public FavoritePromotionController(CreateFavoritePromotion createFavoritePromotion) {
    this.createFavoritePromotion = createFavoritePromotion;
    }

  @PostMapping("/promotions")
  public ResponseEntity<Void> createFavoritePromotion(Authentication authentication, @RequestBody CreateFavoritePromotionRequest body) {
    User userDetails = (User) authentication.getPrincipal();
    Long clientId = userDetails.getId();
    CreateFavoritePromotionInput input = new CreateFavoritePromotionInput(clientId, body.promotionId());
    this.createFavoritePromotion.execute(input);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
