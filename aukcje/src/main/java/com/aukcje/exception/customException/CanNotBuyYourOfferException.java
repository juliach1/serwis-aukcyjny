package com.aukcje.exception.customException;
import com.aukcje.exception.CustomizedException;

public class CanNotBuyYourOfferException extends CustomizedException {
    public CanNotBuyYourOfferException() {
        super("Oferta należy do Ciebie. Nie możesz zakupić tego przedmiotu.");
    }

    public CanNotBuyYourOfferException(String message) {
        super(message);
    }
}
