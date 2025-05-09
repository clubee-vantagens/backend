package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.app.dto.request.CompanyRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "terms_of_use", nullable = false)
    private boolean termsOfUse;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_terms_of_use")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime dateTermsOfUse;

    public Company(CompanyRequestDto dto) {
        this.companyName = dto.companyName();
        this.cnpj = dto.cnpj();
        this.phoneNumber = dto.contactPhone();
        this.termsOfUse = dto.termsOfUse();
        this.dateTermsOfUse = LocalDateTime.now();
    }

    public Company() {
    }

    public LocalDateTime dateNow() {
        return LocalDateTime.now();
    }
}
