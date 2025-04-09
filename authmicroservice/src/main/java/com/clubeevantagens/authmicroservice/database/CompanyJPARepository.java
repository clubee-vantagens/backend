package com.clubeevantagens.authmicroservice.database;

import com.clubeevantagens.authmicroservice.model.data.Company;
import com.clubeevantagens.authmicroservice.model.data.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyJPARepository extends JpaRepository<Company, Long> {
  Optional<Company> findCompanyByUser(User user);
  boolean existsByCnpj(String cnpj);
  boolean existsByUserEmail(String email);
  Optional<Company> findByCnpj(String cnpj);
  @Query(value = """
    SELECT 
        c.id AS companyId,
        c.company_name AS companyName,
        cat.name AS category,
        CASE 
            WHEN fc.client_id IS NOT NULL THEN TRUE
            ELSE FALSE
        END AS isFavorite
    FROM 
        company c
    JOIN 
        category cat ON c.category_id = cat.id
    JOIN 
        promotions p ON p.company_id = c.id
    LEFT JOIN 
        favorite_companies fc ON fc.company_id = c.id AND fc.client_id = :clientId
    WHERE 
        cat.name IN (:categories)
    GROUP BY 
        c.id, c.company_name, cat.name, fc.client_id
    HAVING 
        SUM(p.total_redemptions) > 0
    ORDER BY 
        SUM(p.total_redemptions) DESC
    LIMIT 10
""", nativeQuery = true)
  List<Tuple> findCompanyByCategory(
          @Param("categories") List<String> categories,
          @Param("clientId") Long clientId
  );
}
