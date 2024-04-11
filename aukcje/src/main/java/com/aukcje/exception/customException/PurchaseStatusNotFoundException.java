package com.aukcje.exception.customException;
import com.aukcje.exception.CustomizedException;

public class PurchaseStatusNotFoundException extends CustomizedException {
    public PurchaseStatusNotFoundException(String purchaseStatusName) {
        super("Status zamówienia \"" + purchaseStatusName + "\" nie został znaleziony w bazie");
    }

    public PurchaseStatusNotFoundException(Integer purchaseStatusId) {
        super("Status zamówienia o id \"" + purchaseStatusId + "\" nie został znaleziony w bazie");
    }

}
