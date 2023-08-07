package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class AddressEditPermissionDeniedException extends CustomizedException {

    public AddressEditPermissionDeniedException() {
        super("Odmowa dostępu - adres nie należy do użytkownika");
    }

    public AddressEditPermissionDeniedException(String message) {
        super(message);
    }
}
