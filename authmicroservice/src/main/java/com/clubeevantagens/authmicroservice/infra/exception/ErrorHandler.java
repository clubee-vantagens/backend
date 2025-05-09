package com.clubeevantagens.authmicroservice.infra.exception;

import com.clubeevantagens.authmicroservice.infra.exception.address.PostalCodeNotFoundException;
import com.clubeevantagens.authmicroservice.infra.exception.general.ClientUnavailableException;
import com.clubeevantagens.authmicroservice.infra.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.infra.exception.general.UniqueValueInUse;
import com.clubeevantagens.authmicroservice.infra.exception.security.InvalidTokenException;
import com.clubeevantagens.authmicroservice.infra.exception.user.UserDisabledException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ClientUnavailableException.class)
    private ResponseEntity<ErrorMessage> clientUnavailableExceptionHandler(ClientUnavailableException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessage> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(UniqueValueInUse.class)
    private ResponseEntity<ErrorMessage> uniqueValueInUseExceptionHandler(UniqueValueInUse e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(UserDisabledException.class)
    private ResponseEntity<ErrorMessage> userDisabledExceptionHandler(UserDisabledException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(PostalCodeNotFoundException.class)
    private ResponseEntity<ErrorMessage> postalCodeNotFoundExceptionHandler(PostalCodeNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<ErrorMessage> invalidTokenExceptionHandler(InvalidTokenException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Validation error: " + errorMessage
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String errorMessage = "Data integrity violation.";

        if (e.getCause() instanceof ConstraintViolationException constraintEx) {
            boolean emailUniqueViolated = constraintEx.getConstraintViolations().stream()
                    .anyMatch(violation -> violation.getMessage().contains("EMAIL_UNIQUE"));
            if (emailUniqueViolated) {
                errorMessage = "E-mail already in use.";
            }
        }

        ErrorMessage error = new ErrorMessage(HttpStatus.CONFLICT, errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
