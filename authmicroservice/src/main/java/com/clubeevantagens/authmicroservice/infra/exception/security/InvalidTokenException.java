package com.clubeevantagens.authmicroservice.infra.exception.security;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
