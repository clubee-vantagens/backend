package com.clubeevantagens.authmicroservice.infra.exception.general;

public class ClientUnavailableException extends RuntimeException {
    public ClientUnavailableException(String message) {
        super(message);
    }
}
