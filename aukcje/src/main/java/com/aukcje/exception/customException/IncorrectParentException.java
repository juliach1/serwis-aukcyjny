package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class IncorrectParentException extends CustomizedException {

    public IncorrectParentException() {
        super("Wybrano nieprawidłową kategorię nadrzędną");
    }

    public IncorrectParentException(String message) {
        super(message);
    }
}
