package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class OfferEditPermissionDeniedException extends CustomizedException {

    public OfferEditPermissionDeniedException() {
        super("Oferta należy do innego użytkownika, nie możesz jej edytować");
    }

    public OfferEditPermissionDeniedException(String message) {
        super(message);
    }
}
