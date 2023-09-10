package com.aukcje.service.iface;

import com.aukcje.model.OfferPurchaseModel;

import java.util.List;

public interface OfferPurchaseService {

    //TODO DOKOŃCZYĆ

    void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId);
}
