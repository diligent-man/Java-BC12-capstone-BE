package com.ndt.capstone.validation;

import java.util.Arrays;


import com.ndt.capstone.annotation.ValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private long maxSize;

    private String[] allowedTypes;


    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // 1. Check if file is provided
        if (file == null || file.isEmpty()) {
            buildCustomMessage(context, "File must not be empty.");
            return false;
        }

        // 2. Validate file size
        if (file.getSize() > maxSize) {
            buildCustomMessage(context, "File size exceeds the allowed limit.");
            return false;
        }

        // 3. Validate content type
        String contentType = file.getContentType();
        boolean isValidType = Arrays.asList(allowedTypes).contains(contentType);
        if (!isValidType) {
            buildCustomMessage(context, "Unsupported file format: " + contentType);
            return false;
        }

        return true;
    }


    private void buildCustomMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
    }
}
