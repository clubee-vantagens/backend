package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.Company;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesProjection;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {
    Long save(Company company);
    List<Company> findAll();
    Long deleteById(Long companyId);
    Optional<Company> findById(Long companyId);
    Optional<Company> findCompanyByUser(User user);
    boolean existsByCnpj(String cnpj);
    boolean existsByUserEmail(String email);
    Optional<Company> findByCnpj(String cnpj);
    List<GetCompaniesByCategoriesProjection> findCompanyByCategory(@Param("categories") List<String> categories, @Param("clientId") Long clientId);
}
