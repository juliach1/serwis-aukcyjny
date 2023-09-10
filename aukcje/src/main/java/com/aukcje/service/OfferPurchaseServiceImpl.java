package com.aukcje.service;

import com.aukcje.entity.Address;
import com.aukcje.entity.Offer;
import com.aukcje.entity.OfferPurchaseInfo;
import com.aukcje.entity.User;
import com.aukcje.model.OfferPurchaseModel;
import com.aukcje.repository.AddressRepository;
import com.aukcje.repository.OfferRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.OfferPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferPurchaseServiceImpl implements OfferPurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) {
        List<OfferPurchaseInfo> offerPurchaseInfos = new ArrayList<>();

        for(OfferPurchaseModel offerPurchaseModel: offerPurchaseModels){
            OfferPurchaseInfo offerPurchaseInfo = new OfferPurchaseInfo();

            //TODO ZAMIENIĆ NA MAPPER
            Offer offer = offerRepository.findById(offerPurchaseModel.getOfferId()).orElse(null);
            User seller = userRepository.findById(offer != null ? offer.getUser().getId() : -100).orElse(null);
            User buyer = userRepository.findById(buyerId).orElse(null);
            Address address = addressRepository.findById(addressId).orElse(null);

            LocalDateTime purchaseTime = LocalDateTime.now();

            //todo address
                    //todo purchase status

            offerPurchaseInfo.setPurchaseTime(purchaseTime);
            offerPurchaseInfo.setSeller(seller);
            offerPurchaseInfo.setBuyer(buyer);
            offerPurchaseInfo.setOffer(offer);
            offerPurchaseInfo.setAddress(address);
            offerPurchaseInfo.setQuantity(offerPurchaseModel.getQuantity());
            offerPurchaseInfo.setPrice(offerPurchaseModel.getPrice());

            offerPurchaseInfos.add(offerPurchaseInfo);
        }
        System.out.println("OFFERPURCHASEINFOS SIZE: "+offerPurchaseInfos.size());
    }
}
