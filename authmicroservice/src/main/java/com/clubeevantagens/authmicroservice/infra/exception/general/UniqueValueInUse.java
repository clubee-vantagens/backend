package com.clubeevantagens.authmicroservice.infra.exception.general;

public class UniqueValueInUse extends RuntimeException {
    public UniqueValueInUse(String message) {
        super(message);
    }
}
