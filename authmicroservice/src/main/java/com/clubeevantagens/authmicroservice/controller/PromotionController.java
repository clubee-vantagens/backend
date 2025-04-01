package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.*;
import com.clubeevantagens.authmicroservice.service.CreatePromotion;
import com.clubeevantagens.authmicroservice.service.GetMostRescuedPromotionsInLast7Days;
import com.clubeevantagens.authmicroservice.service.GetPromotionsCreatedInLast7Days;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
  private final GetMostRescuedPromotionsInLast7Days getMostRescuedPromotionsInLast7Days;
  private final GetPromotionsCreatedInLast7Days getPromotionsCreatedInLast7Days;
  private final CreatePromotion createPromotion;

  public PromotionController(GetMostRescuedPromotionsInLast7Days getMostRescuedPromotionsInLast7Days,
                             GetPromotionsCreatedInLast7Days getPromotionsCreatedInLast7Days,
                             CreatePromotion createPromotion) {
    this.getMostRescuedPromotionsInLast7Days = getMostRescuedPromotionsInLast7Days;
    this.getPromotionsCreatedInLast7Days = getPromotionsCreatedInLast7Days;
    this.createPromotion = createPromotion;
  }

  @PostMapping
  public ResponseEntity<Void> createPromotion(Authentication authentication, @RequestBody CreatePromotionRequest body) {
    User userDetails = (User) authentication.getPrincipal();
    Long companyId = userDetails.getId();
    CreatePromotionInput input = new CreatePromotionInput(companyId, body.promotionName(), body.points(), body.promotionImage());
    this.createPromotion.execute(input);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/latest")
  public ResponseEntity<List<GetPromotionsCreatedInLast7DaysOutput>> getPromotionsCreatedInLast7Days(
          Authentication authentication
  ) {
    User userDetails = (User) authentication.getPrincipal();
    Long clientId = userDetails.getId();

    var input = new GetPromotionsCreatedInLast7DaysInput(clientId);

    List<GetPromotionsCreatedInLast7DaysOutput> promotions = getPromotionsCreatedInLast7Days.execute(input);

    return ResponseEntity.ok(promotions);
  }

  @GetMapping("/most-rescued")
  public ResponseEntity<List<GetMostRescuedPromotionsInLast7DaysOutput>> getMostRescuedPromotionsInLast7Days(
          Authentication authentication
  ) {
    User userDetails = (User) authentication.getPrincipal();
    Long clientId = userDetails.getId();

    var input = new GetMostRescuedPromotionsInLast7DaysInput(clientId);

    List<GetMostRescuedPromotionsInLast7DaysOutput> promotions = getMostRescuedPromotionsInLast7Days.execute(input);

    return ResponseEntity.ok(promotions);
  }
}

