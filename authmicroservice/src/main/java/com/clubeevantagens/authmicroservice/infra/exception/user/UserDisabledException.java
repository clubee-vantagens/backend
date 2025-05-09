package com.clubeevantagens.authmicroservice.infra.exception.user;

public class UserDisabledException extends RuntimeException{

    public UserDisabledException(String message){
        super(message);
    }

}
