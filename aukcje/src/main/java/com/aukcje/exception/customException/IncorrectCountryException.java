package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class IncorrectCountryException extends CustomizedException {

    public IncorrectCountryException(){
        super("Nieprawidłowy kraj adresu");
    }

    public IncorrectCountryException(String message){
        super(message);
    }

}
