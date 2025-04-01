package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.request.CompanyRequestDto;
import com.clubeevantagens.authmicroservice.model.dto.request.CompanyUpdateRequestDto;
import com.clubeevantagens.authmicroservice.model.dto.response.ClientResponseDto;
import com.clubeevantagens.authmicroservice.model.dto.response.CompanyResponseDto;
import com.clubeevantagens.authmicroservice.service.CompanyService;
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

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
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
}
