package com.aukcje.validator;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp = "[^0]+")
@NotBlank()
public @interface ValidNotZeroOrBlank {

    public abstract String message() default "Podaj cenę";

    public abstract Class<?>[] groups() default {};

    public abstract Class<?>[] payload() default {};
}
