package com.clubeevantagens.authmicroservice.infra.controller;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesInput;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesOutput;
import com.clubeevantagens.authmicroservice.app.dto.request.CompanyRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.CompanyUpdateRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.CompanyResponseDto;
import com.clubeevantagens.authmicroservice.app.usecase.CompanyService;
import com.clubeevantagens.authmicroservice.app.usecase.GetCompaniesByCategories;
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
@RequestMapping("/api/users/companies")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Company Management", description = "Endpoints para gerenciamento de empresas (usuários comuns e administradores)")
public class CompanyController {
    private final CompanyService companyService;
    private final GetCompaniesByCategories getCompaniesByCategories;

    public CompanyController(CompanyService companyService, GetCompaniesByCategories getCompaniesByCategories) {
        this.companyService = companyService;
        this.getCompaniesByCategories = getCompaniesByCategories;
    }

    @PostMapping("/register")
    public ResponseEntity<CompanyResponseDto> register(@RequestBody @Valid CompanyRequestDto companyRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.register(companyRequestDTO));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAll());
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<CompanyResponseDto> update(@RequestBody CompanyUpdateRequestDto dto, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        CompanyResponseDto response =  companyService.update(dto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDto> update(@PathVariable Long id, @RequestBody CompanyUpdateRequestDto dto) {
        CompanyResponseDto response =  companyService.update(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Void> delete(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        companyService.delete(userDetails.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<CompanyResponseDto> getById(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        CompanyResponseDto response =  companyService.getById(userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDto> getById(@PathVariable Long id) {
        CompanyResponseDto response =  companyService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{cnpj}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDto> getByCnpj(@PathVariable String cnpj) {
        CompanyResponseDto response =  companyService.getByCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Lista todas as companias baseadas nas preferencias escolhidas pelo usuário",
            description = "Retorna dados de acordo com as preferencias do usuário"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista companhias baseadas nas preferências do usuário",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetCompaniesByCategoriesOutput.class)))
    )
    @GetMapping("/categories")
    public ResponseEntity<List<GetCompaniesByCategoriesOutput>> getCompaniesByCategories(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        List<GetCompaniesByCategoriesOutput> response = getCompaniesByCategories.execute(new GetCompaniesByCategoriesInput(userDetails.getId()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
