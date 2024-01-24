package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class UserNotFoundException extends CustomizedException {

    public UserNotFoundException() {
        super("Nie znaleziono użytkownika");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
