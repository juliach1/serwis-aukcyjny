package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class OfferDeletePermissionDeniedException extends CustomizedException {
    public OfferDeletePermissionDeniedException() {
        super("Oferta należy do innego użytkownika, nie możesz jej edytować");
    }

    public OfferDeletePermissionDeniedException(String message) {
        super(message);
    }
}
