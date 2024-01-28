package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class UserStatusNotFoundException extends CustomizedException {

//    public UserStatusNotFoundException(){ super("Nie znaleziono statusu"); }

    public UserStatusNotFoundException(String statusName) {
        super("Nie znaleziono statusu "+statusName);
    }
}
