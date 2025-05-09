package com.clubeevantagens.authmicroservice.infra.controller;

import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.ClientRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.ClientUpdateRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.ClientResponseDto;
import com.clubeevantagens.authmicroservice.app.usecase.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@RestController
@RequestMapping("/api/users/clients")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Client Management", description = "Endpoints para gerenciamento de clientes (usuários comuns e administradores)")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            summary = "Registrar novo cliente",
            description = "Endpoint público para cadastro de novos clientes."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cliente registrado com sucesso",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> register(@RequestBody @Valid ClientRequestDto clientRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.register(clientRequestDTO));
    }

    @Operation(
            summary = "Listar todos os clientes (ADMIN)",
            description = "Retorna todos os clientes cadastrados. Requer permissão de administrador."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientResponseDto.class)))
    )
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }

    @Operation(
            summary = "Atualizar dados do próprio cliente",
            description = "Permite que o cliente autenticado atualize seus próprios dados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dados atualizados com sucesso",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @PutMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> update(@RequestBody ClientUpdateRequestDto dto, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        ClientResponseDto response =  clientService.update(userDetails.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Atualizar cliente por ID (ADMIN)",
            description = "Permite que um administrador atualize os dados de qualquer cliente pelo ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dados atualizados com sucesso",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> update(@PathVariable Long id, @RequestBody ClientUpdateRequestDto dto) {
        ClientResponseDto response =  clientService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Excluir conta do próprio cliente",
            description = "Permite que o cliente autenticado exclua sua própria conta."
    )
    @ApiResponse(responseCode = "204", description = "Conta excluída com sucesso")
    @DeleteMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> delete(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        clientService.delete(userDetails.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
            summary = "Excluir cliente por ID (ADMIN)",
            description = "Permite que um administrador exclua qualquer cliente pelo ID."
    )
    @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
            summary = "Obter dados do próprio cliente",
            description = "Retorna os dados do cliente autenticado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dados do cliente",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getById(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        ClientResponseDto response =  clientService.getById(userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Obter cliente por ID (ADMIN)",
            description = "Retorna os detalhes de um cliente pelo ID. Requer permissão de administrador."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dados do cliente",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id) {
        ClientResponseDto response =  clientService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Obter cliente por e-mail (ADMIN)",
            description = "Retorna os detalhes de um cliente pelo e-mail. Requer permissão de administrador."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dados do cliente",
            content = @Content(schema = @Schema(implementation = ClientResponseDto.class))
    )
    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getByEmail(@PathVariable String email) {
        ClientResponseDto response =  clientService.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
