package com.aukcje.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceValidator implements ConstraintValidator<ValidPrice, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final  String PRICE_PATTERN = "^\\d{1,5}$|(?=^.{1,5}$)^\\d+\\.\\d{0,2}$";


    @Override
    public boolean isValid(String price, ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(PRICE_PATTERN);

        if(price!=null && !price.isBlank()) {
            matcher = pattern.matcher(price);
            return matcher.matches();
        }else
            return true;

    }
}
