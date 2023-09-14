package com.aukcje.service.iface;

import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.OfferPurchaseModel;

import java.util.List;

public interface OfferPurchaseService {

    //TODO DOKOŃCZYĆ

    void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) throws UserNotFoundException, AddressNotFoundException, OfferNotFoundException, OfferNotActiveException, PurchaseStatusNotFoundException, OfferStatusNotFoundException;
}
