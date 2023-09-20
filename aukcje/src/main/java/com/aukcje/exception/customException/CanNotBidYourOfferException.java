package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class CanNotBidYourOfferException extends CustomizedException {
    public CanNotBidYourOfferException() {
        super("Oferta należy do Ciebie. Nie możesz brać udział w jej licytacji!");
    }

    public CanNotBidYourOfferException(String message) {
        super(message);
    }
}
