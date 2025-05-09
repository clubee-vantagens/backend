package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryType name);
    Set<Category> findByNameIn(Collection<CategoryType> name);
    @Query(value = """
        SELECT c.name 
        FROM category c
        INNER JOIN client_preferences cp ON cp.category_id = c.id
        WHERE cp.client_id = :clientId
        """, nativeQuery = true)
    List<String> findCategoriesByClientId(@Param("clientId") Long clientId);
}
