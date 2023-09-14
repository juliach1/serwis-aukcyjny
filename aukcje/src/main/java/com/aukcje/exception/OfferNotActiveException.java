package com.aukcje.exception;

public class OfferNotActiveException extends CustomizedException{

    public OfferNotActiveException() { super("Wybrana oferta nie jest aktywna"); }

    public OfferNotActiveException(Long id) { super("Oferta " + id + " nie jest aktywna"); }


    public OfferNotActiveException(String message) {
        super(message);
    }

}
