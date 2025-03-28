package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.document.FavoritePromotionDocs;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionRequest;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.CreateFavoritePromotion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoritePromotionController implements FavoritePromotionDocs {
  private final CreateFavoritePromotion createFavoritePromotion;
  private final JwtUtils jwtUtils;

  public FavoritePromotionController(CreateFavoritePromotion createFavoritePromotion, JwtUtils jwtUtils) {
    this.createFavoritePromotion = createFavoritePromotion;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/promotions")
  @Override
  public ResponseEntity<Void> createFavoritePromotion(@RequestHeader("Authorization") String authorization, @RequestBody CreateFavoritePromotionRequest body) {
    String accessToken = authorization.substring(7);
    var tokenClaims = jwtUtils.extractAccessToken(accessToken);
    var clientIdStr = tokenClaims.get("sub");
    Long clientId = Long.valueOf(clientIdStr);
    CreateFavoritePromotionInput input = new CreateFavoritePromotionInput(clientId, body.promotionId());
    this.createFavoritePromotion.execute(input);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
