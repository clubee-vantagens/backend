package com.clubeevantagens.authmicroservice.model.data;

import com.clubeevantagens.authmicroservice.model.dto.request.ClientRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Table(name = "client", schema = "public")
@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf",unique = true)
    private String cpf;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "terms_of_use", nullable = false)
    private Boolean termsOfUse;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_terms_of_use")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime dateTermsOfUse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "social_name")
    private String socialName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "client")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "client_preferences",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> preferences;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDate birthDate;

    @Column(name = "photo")
    private String photo;

    public Client(ClientRequestDto dto){
        this.name = dto.name();
        this.cpf = dto.cpf();
        this.phoneNumber = dto.phoneNumber();
        this.termsOfUse = dto.termsOfUse();
        this.dateTermsOfUse = LocalDateTime.now();
        this.socialName = dto.socialName();
        this.address = new Address();
        this.address.setCep(dto.cep());
        this.birthDate = dto.birthDate();
        this.photo = dto.photo();
    }

    public Client() {
    }
}