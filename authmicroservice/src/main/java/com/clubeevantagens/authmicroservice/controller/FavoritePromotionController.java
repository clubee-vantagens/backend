package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionInput;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionRequest;
import com.clubeevantagens.authmicroservice.model.dto.GetTotalFavoritesInput;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.CreateFavoritePromotion;
import com.clubeevantagens.authmicroservice.service.GetTotalFavorites;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoritePromotionController {
  private final CreateFavoritePromotion createFavoritePromotion;
  private final GetTotalFavorites getTotalFavorites;

  public FavoritePromotionController(CreateFavoritePromotion createFavoritePromotion, GetTotalFavorites getTotalFavorites) {
    this.createFavoritePromotion = createFavoritePromotion;
    this.getTotalFavorites = getTotalFavorites;
  }

  @PostMapping("/promotions")
  public ResponseEntity<Void> createFavoritePromotion(Authentication authentication, @RequestBody CreateFavoritePromotionRequest body) {
    User userDetails = (User) authentication.getPrincipal();
    Long clientId = userDetails.getId();
    CreateFavoritePromotionInput input = new CreateFavoritePromotionInput(clientId, body.promotionId());
    this.createFavoritePromotion.execute(input);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(
          summary = "Obtem o total de favoritos do usuário",
          description = "Obtem o total de favoritos do usuário"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Obtem o total de favoritos do usuário"
  )
  @GetMapping("/total")
  public ResponseEntity<Integer> getTotalFavorites(Authentication authentication) {
      User userDetails = (User) authentication.getPrincipal();
      Long clientId = userDetails.getId();
    GetTotalFavoritesInput input = new GetTotalFavoritesInput(clientId);
    Integer total = this.getTotalFavorites.execute(input);
    return ResponseEntity.status(HttpStatus.OK).body(total);
  }
}
