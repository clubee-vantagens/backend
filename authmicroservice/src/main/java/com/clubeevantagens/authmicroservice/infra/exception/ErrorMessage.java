package com.clubeevantagens.authmicroservice.infra.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public record ErrorMessage(
        HttpStatus status,
        String message,
        Date timestamp
) {
    public ErrorMessage(HttpStatus status, String message) {
        this(status, message, new Date());
    }
}
