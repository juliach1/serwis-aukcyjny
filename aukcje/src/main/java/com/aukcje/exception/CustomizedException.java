package com.aukcje.exception;

public class CustomizedException extends Exception {

    public CustomizedException() {
        super("Wystąpił błąd serwera");
    }

    public CustomizedException(String message) {
        super(message);
    }
}
