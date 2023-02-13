package com.aukcje.validator;

import com.aukcje.dto.OfferSearchDTO;
import com.aukcje.model.OfferSearchModel;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLowerOrEqualMaxValidator implements ConstraintValidator<RangeValidator, OfferSearchModel> {


    @Override
    public void initialize(RangeValidator constraintAnnotation) {

    }


    @Override
    public boolean isValid(OfferSearchModel value, ConstraintValidatorContext context)
    {

        if (value.getMinPrice() == null || value.getMaxPrice() == null) {
            return true;
        }
        return value.getMinPrice() <= value.getMaxPrice();
    }


}
