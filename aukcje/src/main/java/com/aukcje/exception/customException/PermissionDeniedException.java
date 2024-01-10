package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class PermissionDeniedException extends CustomizedException {

    public PermissionDeniedException() {
        super("Odmowa dostępu. Nie masz uprawnień do wykonania operacji.");
    }

    public PermissionDeniedException(String message) {
        super(message);
    }
}
