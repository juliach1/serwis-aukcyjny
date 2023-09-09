package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class NoSuchCategoryException extends CustomizedException {
    public NoSuchCategoryException() {
        super("Nie znaleziono kategorii");
    }

    public NoSuchCategoryException(String message) {
        super(message);
    }
}
