package com.aukcje.exception.customException;

import com.aukcje.exception.CustomizedException;

public class OfferNotActiveException extends CustomizedException {

    public OfferNotActiveException() { super("Wybrana oferta nie jest aktywna. Prawdopodobnie została usunięta albo już się zakończyła"); }

    public OfferNotActiveException(Long id) { super("Oferta " + id + " nie jest aktywna. Prawdopodobnie została usunięta albo już się zakończyła"); }

    public OfferNotActiveException(String title) { super("Oferta \"" + title + "\" nie jest aktywna. Prawdopodobnie została usunięta albo już się zakończyła"); }



}
