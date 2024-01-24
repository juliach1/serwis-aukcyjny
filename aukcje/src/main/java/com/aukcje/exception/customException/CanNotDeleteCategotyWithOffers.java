package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class CanNotDeleteCategotyWithOffers extends CustomizedException {
    public CanNotDeleteCategotyWithOffers() {
        super("Nie możesz usunąć kategorii, która zawiera oferty.");
    }

    public CanNotDeleteCategotyWithOffers(String message) {
        super(message);
    }
}
