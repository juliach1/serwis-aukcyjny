package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class CouldNotRateUser extends CustomizedException {

    public CouldNotRateUser() {
        super("Nie udało się dodać oceny dla użytkownika");
    }

    public CouldNotRateUser(String message) {
        super(message);
    }
}
