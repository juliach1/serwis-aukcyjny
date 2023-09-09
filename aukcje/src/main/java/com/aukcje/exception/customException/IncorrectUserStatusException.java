package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class IncorrectUserStatusException extends CustomizedException {

    public IncorrectUserStatusException() {
        super("Wybrano nieprawidłowy status użytkownika");
    }

    public IncorrectUserStatusException(String message) {
        super(message);
    }

}
