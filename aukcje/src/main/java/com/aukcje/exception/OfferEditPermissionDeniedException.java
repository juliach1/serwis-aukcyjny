package com.aukcje.exception;

public class OfferEditPermissionDeniedException extends CustomizedException {

    public OfferEditPermissionDeniedException() {
        super("Oferta należy do innego użytkownika, nie możesz jej edytować");
    }

    public OfferEditPermissionDeniedException(String message) {
        super(message);
    }
}
