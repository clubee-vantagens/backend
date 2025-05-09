package com.clubeevantagens.authmicroservice.app.validator.validSingleCategory;

import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidSingleCategoryValidator implements ConstraintValidator<ValidSingleCategory, String> {

    @Override
    public boolean isValid(String displayName, ConstraintValidatorContext context) {
        if (displayName == null || displayName.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Category is required")
                    .addConstraintViolation();
            return false;
        }

        String normalized = displayName.trim().toLowerCase();
        boolean isValid = Arrays.stream(CategoryType.values())
                .anyMatch(type -> type.getDisplayName().toLowerCase().equals(normalized));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid category: " + displayName)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}