package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class OfferStatusNotFoundException extends CustomizedException {

    public OfferStatusNotFoundException() {
        super("Nie znaleziono statusu oferty");
    }
    public OfferStatusNotFoundException(String statusName) {
        super("Nie znaleziono statusu oferty \"" + statusName + "\"");
    }
}
