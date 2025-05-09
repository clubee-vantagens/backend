package com.clubeevantagens.authmicroservice.infra.controller;


import com.clubeevantagens.authmicroservice.infra.exception.ErrorMessage;
import com.clubeevantagens.authmicroservice.app.interfaces.Controller;
import com.clubeevantagens.authmicroservice.app.dto.ForgotPasswordInput;
import com.clubeevantagens.authmicroservice.app.usecase.ForgotPasswordUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/passwords")
@Tag(name = "Forgot password", description = "Endpoint para recuperação de senha")
public class ForgotPasswordController implements Controller<ForgotPasswordInput, ResponseEntity<Void>> {
  private final ForgotPasswordUseCase forgotPasswordUseCase;

  public ForgotPasswordController(ForgotPasswordUseCase forgotPasswordUseCase) {
    this.forgotPasswordUseCase = forgotPasswordUseCase;
  }

  @Operation(
          summary = "Esqueceu a senha",
          description = "Envia e-mail com uma senha aleatória de recuperação para o e-mail do usuário"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
          @ApiResponse(responseCode = "404", description = "User not found",
                  content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
          @ApiResponse(responseCode = "503", description = "Failed to send email",
                  content = @Content(schema = @Schema(implementation =  ErrorMessage.class)))
  })
  @PostMapping("/forgot")
  @Override
  public ResponseEntity<Void> handle(@RequestBody ForgotPasswordInput input) {
    this.forgotPasswordUseCase.execute(input);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
