package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.app.dto.response.ViaCepResponseDto;
import com.clubeevantagens.authmicroservice.domain.enums.States;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @Column(name = "cep", nullable = false, length = 8)
    @NotEmpty()
    private String cep;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address_number")
    private Integer addressNumber;

    @Column(name = "complement")
    private String complement;

    @Column(name = "contact_phone", nullable = false, length = 30)
    private String contactPhone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private States states;

    public void addFromViaCepRequest(ViaCepResponseDto dto){
        this.setStreet(dto.street());
        this.setCity(dto.city());
        this.setDistrict(dto.district());
        this.setStates(States.valueOf(dto.state()));
    }
}