package com.clubeevantagens.authmicroservice.app.validator.validCategories;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CategoryValidator.class)
public @interface ValidCategories {
    String message() default "Invalid categories or insufficient quantity";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 3;
}