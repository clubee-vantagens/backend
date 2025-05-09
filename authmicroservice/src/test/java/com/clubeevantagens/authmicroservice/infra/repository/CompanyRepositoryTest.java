package com.clubeevantagens.authmicroservice.infra.repository;

import com.clubeevantagens.authmicroservice.infra.database.CompanyJPARepository;
import com.clubeevantagens.authmicroservice.infra.database.FavoriteCompanyJPARepository;
import com.clubeevantagens.authmicroservice.infra.database.PromotionJPARepository;
import com.clubeevantagens.authmicroservice.domain.entity.FavoriteCompany;
import com.clubeevantagens.authmicroservice.domain.entity.Promotion;
import com.clubeevantagens.authmicroservice.domain.entity.Address;
import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.entity.Company;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.GetCompaniesByCategoriesProjection;
import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import com.clubeevantagens.authmicroservice.domain.enums.States;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import com.clubeevantagens.authmicroservice.app.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.app.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CompanyRepositoryTest {

  @Autowired
  private FavoriteCompanyJPARepository favoriteCompanyJPARepository;

  @Autowired
  private PromotionJPARepository promotionJPARepository;

  @Autowired
  private CompanyJPARepository companyJPARepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private FavoriteCompanyRepositoryImpl favoriteCompanyRepository;

  private PromotionRepositoryImpl promotionRepository;

  private CompanyRepository companyRepository;

  @BeforeEach
  void setUp() {
    favoriteCompanyRepository = new FavoriteCompanyRepositoryImpl(favoriteCompanyJPARepository);
    companyRepository = new CompanyRepositoryImpl(companyJPARepository);
    promotionRepository = new PromotionRepositoryImpl(promotionJPARepository);
  }

  @AfterEach
  void teardown() {
    companyJPARepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Find Company By User")
  void testFindCompanyByUser() {
    User user = new User();
    user.setEmail("user@teste.com");
    user.setPassword("senha");

    userRepository.save(user);

    Category category = new Category();
    category.setName(CategoryType.PETSHOP);

    categoryRepository.save(category);

    Company company = createCompany("123456780001956", user, category);

    companyRepository.save(company);

    Optional<Company> found = companyRepository.findCompanyByUser(user);
    assertTrue(found.isPresent());
    assertEquals("Empresa Teste", found.get().getCompanyName());
  }

  @Test
  @DisplayName("Exists By CNPJ")
  void testExistsByCnpj() {
    User user = new User();
    user.setEmail("empresa@email.com");
    user.setPassword("abc@123");

    userRepository.save(user);

    Category category = new Category();
    category.setName(CategoryType.ALIMENTACAO);

    categoryRepository.save(category);

    Company company = createCompany("12345678000121", user, category);
    company.setCnpj("99999999000100");

    companyRepository.save(company);

    boolean exists = companyRepository.existsByCnpj("99999999000100");
    assertTrue(exists);
  }

  @Test
  @DisplayName("Find Company By Category")
  void testFindCompanyByCategory() {
    User user1 = new User();
    user1.setEmail("promocoe1@email.com");
    user1.setPassword("abc@213");

    User user2 = new User();
    user2.setEmail("promocoes2@email.com");
    user2.setPassword("abc@214");

    User user = userRepository.save(user1);
    userRepository.save(user2);

    Category category = new Category();
    category.setName(CategoryType.BELEZA_E_ESTETICA);

    categoryRepository.save(category);

    Company company1 = createCompany("12345678000199", user1, category);
    Company company2 = createCompany("12345678000198", user2, category);

    Long companyId1 = companyRepository.save(company1);
    Long companyId2 = companyRepository.save(company2);

    Promotion promotion1 = createPromotion(companyId1, "Hidratante",20);

    Promotion promotion2 = createPromotion(companyId2, "Protetor Solar", 10);

    promotionRepository.save(promotion1);
    promotionRepository.save(promotion2);

    FavoriteCompany favorite = new FavoriteCompany(user1.getId(), companyId1);
    favoriteCompanyRepository.save(favorite);

    List<GetCompaniesByCategoriesProjection> result =
            companyRepository.findCompanyByCategory(List.of("BELEZA_E_ESTETICA"), user.getId());

    System.out.println(result.toString());

    assertFalse(result.isEmpty());
    assertThat(result.size()).isEqualTo(2);
    assertEquals("BELEZA_E_ESTETICA", result.get(0).category());
    assertThat(result.get(0).companyId()).isEqualTo(companyId1);
    assertThat(result.get(0).isFavorite()).isTrue();
    assertThat(result.get(1).companyId()).isEqualTo(companyId2);
    assertThat(result.get(1).isFavorite()).isFalse();
  }

  private Promotion createPromotion(Long companyId, String promotionName, int totalRedemptions) {
    return new Promotion(
            null,
            companyId,
            promotionName,
            150,
            5.0,
            0,
            totalRedemptions,
            0,
            LocalDateTime.now(),
            "url-promotion-image",
            LocalDateTime.now()
    );
  }

  private Address createAddress() {
    Address address = new Address();
    address.setCep("12345678");
    address.setStreet("Rua das Flores");
    address.setDistrict("Centro");
    address.setCity("São Paulo");
    address.setAddressNumber(123);
    address.setComplement("Apto 45");
    address.setContactPhone("11999999999");
    address.setStates(States.SP);
    return address;
  }

  private Company createCompany(String cnpj, User user, Category category) {
    Company company = new Company();
    company.setCompanyName("Empresa Teste");
    company.setCnpj(cnpj);
    company.setUser(user);
    company.setCategory(category);
    company.setAddress(createAddress());
    return company;
  }
}
