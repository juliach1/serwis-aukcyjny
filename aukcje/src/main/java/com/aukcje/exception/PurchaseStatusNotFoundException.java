package com.aukcje.exception;

public class PurchaseStatusNotFoundException extends CustomizedException {
    public PurchaseStatusNotFoundException(String purchaseStatusName) {
        super("Status zamówienia \"" + purchaseStatusName + "\" nie został znaleziony w bazie");
    }

}
