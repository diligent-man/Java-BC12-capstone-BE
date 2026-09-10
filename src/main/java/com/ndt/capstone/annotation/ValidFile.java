package com.ndt.capstone.annotation;

import java.lang.annotation.*;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import com.ndt.capstone.validation.FileValidator;


@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file uploaded";


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};


    // Default: 5MB
    long maxSize() default 5242880;


    String[] allowedTypes() default {"image/jpeg", "image/png", "application/pdf"};
}
