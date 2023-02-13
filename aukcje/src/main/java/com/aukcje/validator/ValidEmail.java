package com.aukcje.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {

    String message() default "Nieprawidłowy adres e-mail";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
