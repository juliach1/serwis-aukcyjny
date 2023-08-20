package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class OfferNotFoundException extends CustomizedException {

    public OfferNotFoundException() {
        super("Nie znaleziono pasującej oferty.");
    }

    public OfferNotFoundException(String message) {
        super(message);
    }
}
