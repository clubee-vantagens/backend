package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.dto.CreatePromotionRequest;
import com.clubeevantagens.authmicroservice.model.dto.GetMostRescuedPromotionsInLast7DaysOutput;
import com.clubeevantagens.authmicroservice.model.dto.GetPromotionsCreatedInLast7DaysOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface PromotionDocs {
  @Operation(
          summary = "Cria uma promoção",
          description = "Extrai o companyId do token de autorização e cria uma promoção com base no corpo da requisição",
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
                          responseCode = "201",
                          description = "Created",
                          content = @Content
                  )
          }
  )
  public ResponseEntity<Void> createPromotion(@RequestHeader("Authorization") String authorization, @RequestBody CreatePromotionRequest body);

  @Operation(
          summary = "Obtem as dez promoções mais recentes",
          description = "Extrai o clientId do token de autorização e retorna as promoções mais recentes.",
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
                          description = "Promoções obtidas com sucesso",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = GetPromotionsCreatedInLast7DaysOutput[].class)
                          )
                  ),
                  @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
                  @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
          }
  )
  public ResponseEntity<List<GetPromotionsCreatedInLast7DaysOutput>> getPromotionsCreatedInLast7Days(@RequestHeader("Authorization") String authorization);

  @Operation(
          summary = "Obtem as 10 promoções mais resgatadas nos ultimos 7 dias",
          description = "Extrai o clientId do token de autorização e retorna as promoções mais resgatadas nos ultimos 7 dias",
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
                          description = "Promoções obtidas com sucesso",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = GetMostRescuedPromotionsInLast7DaysOutput[].class)
                          )
                  ),
                  @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
                  @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
          }
  )
  public ResponseEntity<List<GetMostRescuedPromotionsInLast7DaysOutput>> getMostRescuedPromotionsInLast7Days(@RequestHeader("Authorization") String authorization);
}
