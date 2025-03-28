package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.document.PromotionDocs;
import com.clubeevantagens.authmicroservice.model.dto.*;
import com.clubeevantagens.authmicroservice.service.CreatePromotion;
import com.clubeevantagens.authmicroservice.service.GetMostRescuedPromotionsInLast7Days;
import com.clubeevantagens.authmicroservice.service.GetPromotionsCreatedInLast7Days;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController implements PromotionDocs {
  private final GetMostRescuedPromotionsInLast7Days getMostRescuedPromotionsInLast7Days;
  private final GetPromotionsCreatedInLast7Days getPromotionsCreatedInLast7Days;
  private final CreatePromotion createPromotion;
  private final JwtUtils jwtUtils;

  public PromotionController(GetMostRescuedPromotionsInLast7Days getMostRescuedPromotionsInLast7Days, GetPromotionsCreatedInLast7Days getPromotionsCreatedInLast7Days, CreatePromotion createPromotion, JwtUtils jwtUtils) {
    this.getMostRescuedPromotionsInLast7Days = getMostRescuedPromotionsInLast7Days;
    this.getPromotionsCreatedInLast7Days = getPromotionsCreatedInLast7Days;
    this.createPromotion = createPromotion;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping
  @Override
  public ResponseEntity<Void> createPromotion(@RequestHeader("Authorization") String authorization, @RequestBody CreatePromotionRequest body) {
    String accessToken = authorization.substring(7);
    var tokenClaims = jwtUtils.extractAccessToken(accessToken);
    var companyIdStr = tokenClaims.get("sub");
    Long companyId = Long.valueOf(companyIdStr);
    CreatePromotionInput input = new CreatePromotionInput(companyId, body.promotionName(), body.points(), body.promotionImage());
    this.createPromotion.execute(input);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/latest")
  @Override
  public ResponseEntity<List<GetPromotionsCreatedInLast7DaysOutput>> getPromotionsCreatedInLast7Days(@RequestHeader("Authorization") String authorization) {
    String accessToken = authorization.substring(7);
    var tokenClaims = jwtUtils.extractAccessToken(accessToken);
    var clientIdStr = tokenClaims.get("sub");
    Long clientId = Long.valueOf(clientIdStr);

    var input = new GetPromotionsCreatedInLast7DaysInput(clientId);

    List<GetPromotionsCreatedInLast7DaysOutput> promotions = getPromotionsCreatedInLast7Days.execute(input);

    return ResponseEntity.ok(promotions);
  }

  @GetMapping("/most-rescued")
  @Override
  public ResponseEntity<List<GetMostRescuedPromotionsInLast7DaysOutput>> getMostRescuedPromotionsInLast7Days(@RequestHeader("Authorization") String authorization) {
    String accessToken = authorization.substring(7);
    var tokenClaims = jwtUtils.extractAccessToken(accessToken);
    var clientIdStr = tokenClaims.get("sub");
    Long clientId = Long.valueOf(clientIdStr);

    var input = new GetMostRescuedPromotionsInLast7DaysInput(clientId);

    List<GetMostRescuedPromotionsInLast7DaysOutput> promotions = getMostRescuedPromotionsInLast7Days.execute(input);

    return ResponseEntity.ok(promotions);
  }
}

