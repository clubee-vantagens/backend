package com.clubeevantagens.authmicroservice.infra.exception.address;

public class PostalCodeNotFoundException extends RuntimeException {
    public PostalCodeNotFoundException(String message) {
        super(message);
    }
}
