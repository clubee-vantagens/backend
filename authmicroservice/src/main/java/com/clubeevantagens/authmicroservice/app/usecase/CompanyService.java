package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.infra.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.infra.exception.general.UniqueValueInUse;
import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.entity.Company;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.CompanyUpdateRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.CompanyRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.CompanyResponseDto;
import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import com.clubeevantagens.authmicroservice.domain.enums.Role;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import com.clubeevantagens.authmicroservice.app.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final ViaCepService viaCepService;

    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository,
                          ViaCepService viaCepService) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.viaCepService = viaCepService;
    }

    public CompanyResponseDto register(CompanyRequestDto dto){
        if (companyRepository.existsByUserEmail(dto.email())) {
            throw new UniqueValueInUse(dto.email());
        }

        if(companyRepository.existsByCnpj(dto.cnpj())) {
            throw new UniqueValueInUse(dto.cnpj());
        }

        Company company = new Company(dto);
        company.setUser(new User(dto.email(), passwordEncoder.encode(dto.password())));
        company.getUser().setActive(true);
        company.getUser().setRole(Role.COMPANY);

        company.setCompanyName(dto.companyName());
        setCategory(company, dto.categoryName());
        setupAddress(company, dto.cep(), dto.addressNumber(), dto.complement(), dto.phoneNumber());

        company.setCnpj(dto.cnpj());
        company.setReviews(null);
        company.setPhoneNumber(dto.contactPhone());
        company.setTermsOfUse(dto.termsOfUse());
        company.setDateTermsOfUse(company.dateNow());

        companyRepository.save(company);
        return new CompanyResponseDto(company);
    }

    public List<CompanyResponseDto> getAll() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(CompanyResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CompanyResponseDto update(CompanyUpdateRequestDto dto, Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found."));

        company.setCompanyName(dto.companyName());
        setCategory(company, dto.categoryName());
        setupAddress(company, dto.cep(), dto.addressNumber(), dto.complement(), dto.phoneNumber());

        company.setCnpj(dto.cnpj());
        company.setPhoneNumber(dto.phoneNumber());

        companyRepository.save(company);
        return new CompanyResponseDto(company);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            throw new EntityNotFoundException("Company not found!");
        }
        companyRepository.deleteById(company.get().getId());
    }

    public CompanyResponseDto getById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            throw new EntityNotFoundException("Company not found!");
        }
        return new CompanyResponseDto(company.get());
    }

    public CompanyResponseDto getByCnpj(String cnpj) {
        Optional<Company> company = companyRepository.findByCnpj(cnpj);
        if(company.isEmpty()){
            throw new EntityNotFoundException("Company not found!");
        }
        return new CompanyResponseDto(company.get());
    }

    private void setCategory(Company company, String categoryName) {
        Category category = categoryRepository.findByName(CategoryType.fromDisplayName(categoryName))
                .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
        company.setCategory(category);
        category.getCompanies().add(company);
    }

    private void setupAddress(Company company, String cep, Integer addressNumber, String complement, String phone) {
        company.getAddress().addFromViaCepRequest(viaCepService.requestToViaCep(cep));
        company.getAddress().setAddressNumber(addressNumber);
        company.getAddress().setComplement(complement);
        company.getAddress().setContactPhone(phone);
    }
}
