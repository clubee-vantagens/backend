package com.clubeevantagens.authmicroservice.infra.exception.general;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
