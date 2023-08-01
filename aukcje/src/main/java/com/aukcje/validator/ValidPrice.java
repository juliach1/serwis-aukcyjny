package com.aukcje.validator;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation

public @interface ValidPrice {

    public abstract String message() default "Podaj prawidłową cenę!";

    public abstract Class<?>[] groups() default {};

    public abstract Class<?>[] payload() default {};
}
