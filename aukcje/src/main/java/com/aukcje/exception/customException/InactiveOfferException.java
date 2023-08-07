package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class InactiveOfferException extends CustomizedException {

    public InactiveOfferException() {
        super("Oferta jest nieaktywna - przedmiot został sprzedany lub usunięty");
    }

    public InactiveOfferException(String message) {
        super(message);
    }
}
