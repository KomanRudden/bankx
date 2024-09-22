package com.komanrudden.model.annotations;

import com.komanrudden.model.annotations.validators.ValidBankNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidBankNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBankName {
    String message() default "String must not contain leading or trailing spaces, no special characters and be uppercase";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

