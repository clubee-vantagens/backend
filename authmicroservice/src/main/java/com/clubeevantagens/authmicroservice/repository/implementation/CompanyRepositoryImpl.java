package com.clubeevantagens.authmicroservice.repository.implementation;

import com.clubeevantagens.authmicroservice.database.CompanyJPARepository;
import com.clubeevantagens.authmicroservice.model.data.Company;
import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.GetCompaniesByCategoriesProjection;
import com.clubeevantagens.authmicroservice.repository.CompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {
  private final CompanyJPARepository connection;

  public CompanyRepositoryImpl(CompanyJPARepository connection) {
    this.connection = connection;
  }

  @Override
  public Long save(Company company) {
    return connection.save(company).getId();
  }

  @Override
  public List<Company> findAll() {
    return connection.findAll();
  }

  @Override
  public Long deleteById(Long companyId) {
    connection.deleteById(companyId);
    return companyId;
  }

  @Override
  public Optional<Company> findById(Long companyId) {
    return connection.findById(companyId);
  }

  @Override
  public Optional<Company> findCompanyByUser(User user) {
    return connection.findCompanyByUser(user);
  }

  @Override
  public boolean existsByCnpj(String cnpj) {
    return connection.existsByCnpj(cnpj);
  }

  @Override
  public boolean existsByUserEmail(String email) {
    return connection.existsByUserEmail(email);
  }

  @Override
  public Optional<Company> findByCnpj(String cnpj) {
    return connection.findByCnpj(cnpj);
  }

  @Override
  public List<GetCompaniesByCategoriesProjection> findCompanyByCategory(List<String> categories, Long clientId) {
    return connection.findCompanyByCategory(categories, clientId)
            .stream()
            .map(tuple -> new GetCompaniesByCategoriesProjection(
                    tuple.get("companyId", Long.class),
                    tuple.get("companyName", String.class),
                    tuple.get("category", String.class),
                    tuple.get("isFavorite", Boolean.class)
            ))
            .collect(Collectors.toList());
  }
}

