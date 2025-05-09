package com.clubeevantagens.authmicroservice.app.validator.validCategories;

import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CategoryValidator implements ConstraintValidator<ValidCategories, Set<String>> {

    private int min;

    @Override
    public void initialize(ValidCategories constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Set<String> categories, ConstraintValidatorContext context) {
        if (categories == null) return true;

        Set<String> validCategories = new HashSet<>();
        Set<String> invalidCategories = new HashSet<>();

        for (String displayName : categories) {
            if (displayName == null || displayName.isBlank()) {
                continue;
            }

            boolean isValid = Arrays.stream(CategoryType.values())
                    .anyMatch(type -> type.getDisplayName().equalsIgnoreCase(displayName.trim()));

            if (isValid) {
                validCategories.add(displayName.trim().toLowerCase());
            } else {
                invalidCategories.add(displayName);
            }
        }

        if (!invalidCategories.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid categories: " + invalidCategories)
                    .addConstraintViolation();
            return false;
        }

        if (!validCategories.isEmpty() && validCategories.size() < min) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Select, at least, " + min + " valid categories or none for all of them")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}