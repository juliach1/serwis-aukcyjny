package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class CartOfferNotFoundException extends CustomizedException {

    public CartOfferNotFoundException() {
        super("Nie znaleziono wybranej pozycji w Twoim koszyku.");
    }

    public CartOfferNotFoundException(String message) {
        super(message);
    }

}