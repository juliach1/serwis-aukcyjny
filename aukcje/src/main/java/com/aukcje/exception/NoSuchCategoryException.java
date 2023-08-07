package com.aukcje.exception;

public class NoSuchCategoryException extends CustomizedException{
    public NoSuchCategoryException() {
        super("Nie znaleziono kategorii");
    }

    public NoSuchCategoryException(String message) {
        super(message);
    }
}
