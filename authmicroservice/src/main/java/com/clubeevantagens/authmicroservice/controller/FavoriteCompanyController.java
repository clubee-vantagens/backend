package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoriteCompanyInput;
import com.clubeevantagens.authmicroservice.model.dto.CreateFavoriteCompanyRequest;
import com.clubeevantagens.authmicroservice.service.CreateFavoriteCompany;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteCompanyController {
  private final CreateFavoriteCompany createFavoriteCompany;

  public FavoriteCompanyController(CreateFavoriteCompany createFavoriteCompany) {
    this.createFavoriteCompany = createFavoriteCompany;
  }

  @Operation(
          summary = "Adiciona uma companhia nos favoritos do usuário",
          description = "Salva uma companhia como favorito"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Companhia adicionada aos favoritos com sucesso"
  )
  @PostMapping("/companies")
  public ResponseEntity<Void> createFavoriteCompany(Authentication authentication, @RequestBody CreateFavoriteCompanyRequest body) {
    User userDetails = (User) authentication.getPrincipal();
    Long clientId = userDetails.getId();
    CreateFavoriteCompanyInput input = new CreateFavoriteCompanyInput(clientId, body.companyId());
    this.createFavoriteCompany.execute(input);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
