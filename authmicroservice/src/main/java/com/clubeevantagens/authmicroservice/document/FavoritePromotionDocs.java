package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.dto.CreateFavoritePromotionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface FavoritePromotionDocs {
  @Operation(
          summary = "Cria um promoção favorita",
          description = "Extrai o clientId do token de autorização e cria uma promoção favorita com base no corpo da requisição",
          security = @SecurityRequirement(name = "bearerAuth"),
          parameters = {
                  @Parameter(
                          name = "Authorization",
                          in = ParameterIn.HEADER,
                          description = "Token de acesso no formato Bearer {token}",
                          required = true
                  )
          },
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created",
                          content = @Content
                  )
          }
  )
  public ResponseEntity<Void> createFavoritePromotion(@RequestHeader("Authorization") String authorization, @RequestBody CreateFavoritePromotionRequest body);
}
