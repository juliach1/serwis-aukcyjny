package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class PurchaseNotFoundException extends CustomizedException {

    public PurchaseNotFoundException() {
        super("Nie znaleziono transakcji.");
    }

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
