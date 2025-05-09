package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {
  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  @DisplayName("Find By Name")
  void findByName() {
    Category category = new Category();
    category.setName(CategoryType.PETSHOP);

    categoryRepository.save(category);

    Optional<Category> result = categoryRepository.findByName(CategoryType.PETSHOP);

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo(CategoryType.PETSHOP);
  }

  @Test
  @DisplayName("Find By Name In")
  void findByNameIn() {
    Category category1 = new Category();
    category1.setName(CategoryType.PETSHOP);

    categoryRepository.save(category1);

    Category category2 =  new Category();
    category2.setName(CategoryType.ALIMENTACAO);

    categoryRepository.save(category2);

    Set<Category> result = categoryRepository.findByNameIn(Set.of(CategoryType.PETSHOP, CategoryType.ALIMENTACAO));

    assertThat(result).hasSize(2);
  }

  @Test
  @DisplayName("Find Categories By Client Id")
  void findCategoriesByClientId() {
    // Atualmente não é possível testar
  }
}
