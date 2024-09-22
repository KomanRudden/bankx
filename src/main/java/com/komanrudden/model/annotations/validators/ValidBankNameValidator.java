package com.komanrudden.model.annotations.validators;

import com.komanrudden.model.annotations.ValidBankName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBankNameValidator implements ConstraintValidator<ValidBankName, String> {

        @Override
        public boolean isValid(String s, ConstraintValidatorContext context) {
            if (s == null || s.trim().isEmpty()) {
                return false; // Null or empty strings are considered invalid
            }

            // Check for leading and trailing spaces
            if (!s.equals(s.trim())) {
                return false; // Leading or trailing spaces found
            }

            // Check for special characters (assuming letters and digits are valid)
            if (!s.matches("[a-zA-Z0-9\\s]+")) {
                return false; // Contains special characters
            }

            // Check if the transformed string is equal to the original string
            return s.equals(s.toUpperCase());
        }
}
