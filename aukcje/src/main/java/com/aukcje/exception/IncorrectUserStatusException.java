package com.aukcje.exception;

public class IncorrectUserStatusException extends CustomizedException{

    public IncorrectUserStatusException() {
        super("Wybrano nieprawidłowy status użytkownika");
    }

    public IncorrectUserStatusException(String message) {
        super(message);
    }

}
