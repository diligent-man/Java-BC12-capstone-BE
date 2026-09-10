package com.ndt.capstone.annotation;

import java.lang.annotation.*;


import jakarta.validation.Payload;
import jakarta.validation.Constraint;


import com.ndt.capstone.validation.PriceRangeValidator;


@Target({ElementType.TYPE}) // Applied to the class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceRangeValidator.class)
@Documented
public @interface ValidPriceRange {
    String message() default "Invalid price range";


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
