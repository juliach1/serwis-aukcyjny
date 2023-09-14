package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class AddressNotFoundException extends CustomizedException {
    public AddressNotFoundException() {
        super("Nie znaleziono adresu");
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
