package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class UserNotActiveException extends CustomizedException {

    public UserNotActiveException() {
        super("Twoje konto nie jest aktywne. Tylko aktywni użytkownicy mogą wykonać tę operację!");
    }

    public UserNotActiveException(String message) {
        super(message);
    }
}
