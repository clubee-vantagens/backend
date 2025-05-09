package com.clubeevantagens.authmicroservice.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryType {
    ALIMENTACAO("Alimentação"),
    PETSHOP("PetShop"),
    PRODUTOS_ARTESANAIS("Produtos artesanais"),
    PAPELARIA("Papelaria"),
    FLORES_E_PLANTAS("Flores e plantas"),
    BELEZA_E_ESTETICA("Beleza e estética"),
    MANUTENCAO_AUTOMOVEIS("Manutenção de automóveis"),
    LIMPEZA_AUTOMOVEIS("Limpeza de automóveis"),
    LIVRARIA("Livraria"),
    PERFUMARIA("Perfumaria"),
    VESTUARIO_CALCADOS("Vestuário e calçados"),
    INFORMATICA_ELETRONICOS("Informática e eletrônicos");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public static CategoryType fromDisplayName(String displayName) {
        String normalized = displayName.trim().toLowerCase();
        return Arrays.stream(values())
                .filter(type -> type.getDisplayName().toLowerCase().equals(normalized))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + displayName));
    }
}