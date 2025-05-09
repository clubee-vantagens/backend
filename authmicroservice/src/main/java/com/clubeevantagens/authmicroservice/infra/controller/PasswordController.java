package com.clubeevantagens.authmicroservice.infra.controller;

import com.clubeevantagens.authmicroservice.infra.exception.ErrorMessage;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.UpdatePasswordRequestDto;
import com.clubeevantagens.authmicroservice.app.usecase.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/passwords")
@Tag(name = "Password Management", description = "Endpoints para gerenciamento de senhas e recuperação de conta")
public class PasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Operation(summary = "Atualizar senha atual",
            description = "Permite ao usuário autenticado alterar sua senha usando a senha antiga")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class,
                            example = "Password successfully updated."))),
            @ApiResponse(responseCode = "400", description = "Senha antiga incorreta",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/update/me")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequestDto dto, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.updatePassword(userDetails.getId(), dto.oldPassword(), dto.newPassword()));
    }
}