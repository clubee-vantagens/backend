package com.clubeevantagens.authmicroservice.infra.exception.security;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
