package com.clubeevantagens.authmicroservice.infra.controller;

import com.clubeevantagens.authmicroservice.app.dto.request.AdminReviewRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.ReviewRequestDto;
import com.clubeevantagens.authmicroservice.app.usecase.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Review Management", description = "Endpoints para gerenciamento de reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(
            summary = "Registrar nova review",
            description = "Endpoint público para cadastro de novas reviews."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Review registrada com sucesso",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @PostMapping
    public ResponseEntity<String> registerReview(@RequestBody @Valid ReviewRequestDto dto, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.registerReview(dto, authentication));
    }

    @Operation(
            summary = "Registrar nova review (ADMIN)",
            description = "Permite que um administrador cadastre novas reviews."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Review registrada com sucesso",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @PostMapping("/clients/{clientId}/companies/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createReviewAsAdmin(
            @PathVariable Long clientId,
            @PathVariable Long companyId,
            @RequestBody @Valid AdminReviewRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.registerReview(clientId, companyId, dto));
    }
}