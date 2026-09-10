package com.ndt.capstone.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ndt.capstone.dto.request.PriceRangeDTO;
import com.ndt.capstone.annotation.ValidPriceRange;


public class PriceRangeValidator implements ConstraintValidator<ValidPriceRange, PriceRangeDTO> {

    @Override
    public boolean isValid(PriceRangeDTO dto, ConstraintValidatorContext context) {
        // Let @NotNull handle missing values
        if (dto == null || dto.getMinPrice() == null || dto.getMaxPrice() == null) {
            return true;
        }
        boolean isValid = dto.getMinPrice().compareTo(dto.getMaxPrice()) <= 0;

        if (!isValid) {
            context.disableDefaultConstraintViolation();

            // Build your clean dynamic message string
            String customMessage = String.format(
                "Minimum price (%.4f) must be less than or equal to maximum price (%.4f)",
                dto.getMinPrice(), dto.getMaxPrice()
            );

            // Bind the message cleanly to a specific field property path
            context.buildConstraintViolationWithTemplate(customMessage)
                .addPropertyNode("message")
                .addConstraintViolation();
        }
        return isValid;
    }
}
